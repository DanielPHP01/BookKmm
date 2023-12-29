//
//  MainScreen.swift
//  iosApp
//
//  Created by Daniel on 28/12/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI
import shared
import Shimmer
import Kingfisher

@available(iOS 17.0, *)
struct MainScreen: View {
    @State private var searchText = ""
    @ObservedObject private var viewModel: IOSMainViewModel
    
    init() {
        let kotlinViewModel = ViewModelFactory().createMainViewModel()
        self.viewModel = IOSMainViewModel(kotlinViewModel: kotlinViewModel)
    }
    
    var body: some View {
        VStack {
            TextField("Search", text: $searchText)
                .padding(7)
                .padding(.horizontal, 25)
                .background(Color(.systemGray6))
                .cornerRadius(8)
                .overlay(
                    HStack {
                        Image(systemName: "magnifyingglass")
                            .foregroundColor(.gray)
                            .frame(minWidth: 0, maxWidth: .infinity, alignment: .leading)
                            .padding(.leading, 8)
                            .onTapGesture {
                                viewModel.getBooks(query: searchText)
                            }
                        
                        if !searchText.isEmpty {
                            Button(action: {
                                self.searchText = ""
                            }) {
                                Image(systemName: "multiply.circle.fill")
                                    .foregroundColor(.gray)
                                    .padding(.trailing, 8)
                            }
                        }
                    }
                )
                .padding(.horizontal, 10)
            
            
            if !viewModel.books.isEmpty {
                GeometryReader { geometry in
                    ScrollView(.horizontal, showsIndicators: false) {
                        LazyHStack(spacing: 30) {
                            ForEach(viewModel.books, id: \.id) { book in
                                GeometryReader { itemGeometry in
                                    BookItem(book: book)
                                        .scaleEffect(scaleValue(geometry: geometry, itemGeometry: itemGeometry))
                                        .shadow(radius: 10)
                                        .opacity(opacityValue(geometry: geometry, itemGeometry: itemGeometry))
                                        .transformEffect(perspectiveTransform(geometry: geometry, itemGeometry: itemGeometry))
                                }
                                .frame(width: 200, height: 400)
                            }
                        }
                        .padding(.vertical, 50)
                        .background(LinearGradient(gradient: Gradient(colors: [Color.gray.opacity(0.5), Color.clear]), startPoint: .leading, endPoint: .trailing))
                    }
                    .padding(.top, 20)
                    .ignoresSafeArea(.all)
                    .frame(maxWidth: .infinity)
                }
            }
            
            Spacer()
        }
        .coordinateSpace(name: "scroll")
    }
    
    private func scaleValue(geometry: GeometryProxy, itemGeometry: GeometryProxy) -> CGFloat {
        let itemMidPosition = itemGeometry.frame(in: .global).midX
        let screenMidPosition = geometry.size.width / 2
        let distance = abs(screenMidPosition - itemMidPosition)
        let scale = max(0.9, 1 - distance / geometry.size.width)
        return scale
    }
    
    private func opacityValue(geometry: GeometryProxy, itemGeometry: GeometryProxy) -> Double {
        let itemMidPosition = itemGeometry.frame(in: .global).midX
        let screenMidPosition = geometry.size.width / 2
        let distance = abs(screenMidPosition - itemMidPosition)
        return Double(max(0.5, 1 - distance / (geometry.size.width * 1.5)))
    }
    
    private func perspectiveTransform(geometry: GeometryProxy, itemGeometry: GeometryProxy) -> CGAffineTransform {
        let itemMidPosition = itemGeometry.frame(in: .global).midX
        let screenMidPosition = geometry.size.width / 2
        let distance = screenMidPosition - itemMidPosition
        let maxDistance = geometry.size.width / 2
        let ratio = distance / maxDistance
        
        // Adjust these values to tweak the perspective effect
        let scale = 1.0 + 0.1 * (1 - abs(ratio))
        let xTranslation = ratio * 50
        
        return CGAffineTransform(scaleX: scale, y: scale)
            .translatedBy(x: xTranslation, y: 0)
    }
}


@available(iOS 15.0, *)
struct BookItem: View {
    var book: BookVolume.VolumeItem
    @State private var isLoading = true
    
    var body: some View {
        VStack {
            let imageUrl = ensureHttps(book.volumeInfo?.imageLinks?.thumbnail)
            
            KFImage(URL(string: imageUrl))
                .resizable()
                .placeholder{
                    if isLoading {
                        Image("emtyView")
                            .resizable()
                            .clipped()
                            .frame(maxWidth: .infinity)
                            .frame(height: 400)
                            .padding(.horizontal,16)
                            .onAppear { print(imageUrl ) }
                            .modifier(Shimmer())
                    }
                    else {
                        Image("emtyView")
                            .resizable()
                            .clipped()
                            .frame(maxWidth: .infinity)
                            .frame(height: 400)
                            .padding(.horizontal,16)
                        
                    }}
                .clipped()
                .frame(maxWidth: .infinity)
                .frame(height: 400)
                .padding(.horizontal,16)
            
            
            HStack {
                Text(book.volumeInfo?.title ?? "Unknown title")
                    .font(/*@START_MENU_TOKEN@*/.title/*@END_MENU_TOKEN@*/)
                    .padding(.horizontal,16)
                Spacer()
            }
        }
    }
    // Функция ensureHttps на уровне файла
    func ensureHttps(_ urlString: String?) -> String {
        guard let urlString = urlString, var urlComponents = URLComponents(string: urlString) else {
            return "https://example.com/default.jpg" // URL-адрес изображения-заглушки, если URL изначально недоступен
        }
        
        // Переключение на HTTPS
        urlComponents.scheme = "https"
        return urlComponents.url?.absoluteString ?? urlString
    }
}





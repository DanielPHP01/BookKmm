import SwiftUI
import Combine
import Foundation
import shared

struct ContentView: View {
    @State private var searchText = ""
       @ObservedObject private var viewModel: IOSMainViewModel

       init() {
           let kotlinViewModel = ViewModelFactory().createMainViewModel()
           self.viewModel = IOSMainViewModel(kotlinViewModel: kotlinViewModel)
       }
    
    var body: some View {
        VStack {
            TextField("Search", text: $searchText)
                .textFieldStyle(RoundedBorderTextFieldStyle())
                .padding()

            Button("Search") {
                viewModel.getBooks(query: searchText)
            }
            .padding()

            if !viewModel.books.isEmpty {
                List(viewModel.books, id: \.self) { book in
                    BookItem(book: book)
                }
            }
        }
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}

struct BookItem: View {
    var book: BookVolume.VolumeItem
    
    var body: some View {
        Text(book.volumeInfo?.title ?? "Unknown title")
    }
}

class SwiftBooksUpdateListener: BooksUpdateListener {
    let update: ([BookVolume.VolumeItem]) -> Void

    init(update: @escaping ([BookVolume.VolumeItem]) -> Void) {
        self.update = update
    }

    func onBooksUpdated(books: [BookVolume.VolumeItem]) {
        update(books)
    }
}

class IOSMainViewModel: ObservableObject {
    @Published var books: [BookVolume.VolumeItem] = []
    
    private let kotlinViewModel: MainViewModel
    private var booksUpdateListener: SwiftBooksUpdateListener!
    
    init(kotlinViewModel:MainViewModel) {
        self.kotlinViewModel = kotlinViewModel
        setupObservation()
    }
    
    private func setupObservation() {
        booksUpdateListener = SwiftBooksUpdateListener { [weak self] books in
            DispatchQueue.main.async {
                self?.books = books
            }
        }
        kotlinViewModel.setBooksUpdateListener(listener: booksUpdateListener)
    }
    
    func getBooks(query: String) {
        kotlinViewModel.getBooks(query: query)
    }
}

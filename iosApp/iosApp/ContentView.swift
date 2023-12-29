import SwiftUI
import Combine
import Foundation
import shared

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

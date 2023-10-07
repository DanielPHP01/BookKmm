//import com.example.bookkmm.data.model.BookVolume
//import kotlinx.coroutines.CoroutineScope
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.flow.Flow
//
//actual class MainViewModel actual constructor(
//    private val repository: Repository,
//    private val scope: CoroutineScope
//) {
//    private val _booksFlow = MutableStateFlow<List<BookVolume.VolumeItem>>(emptyList())
//    override val booksFlow: Flow<List<BookVolume.VolumeItem>> get() = _booksFlow
//
//    override fun getBooks(query: String) {
//        repository.getBooks(query)
//            .flowOn(Dispatchers.IO)
//            .onEach { _booksFlow.value = it }
//            .launchIn(scope)
//    }
//}
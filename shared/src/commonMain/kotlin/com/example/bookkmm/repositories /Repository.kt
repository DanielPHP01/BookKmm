import com.example.bookkmm.data.Either
import com.example.bookkmm.data.RemoteDataSource
import com.example.bookkmm.data.model.BookVolume
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class Repository(private val dataSource: RemoteDataSource) {
    fun getBooks(search: String): Flow<Either<String, List<BookVolume.VolumeItem>>> = flow {
        val response = dataSource.getBook(search = search)
        emit(response)
    }.flowOn(Dispatchers.IO)
}
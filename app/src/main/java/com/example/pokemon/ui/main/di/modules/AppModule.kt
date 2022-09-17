import android.content.Context
import com.example.pokemon.ui.main.app.PokemonApp
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val app: PokemonApp) {

    @Singleton
    @Provides
    fun context(): Context {
        return app
    }

    @Singleton
    @Provides
    fun app(): PokemonApp {
        return app
    }
}
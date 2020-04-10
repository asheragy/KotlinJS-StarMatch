import kotlinx.css.Color
import react.*
import react.dom.div
import react.dom.render
import kotlin.browser.document

fun main() {
    render(document.getElementById("root")) {
        child(App::class) {}
    }
}

class App : RComponent<RProps, RState>() {
    override fun RBuilder.render() {
        child(StarMatch::class) {}
    }
}

const val MaxStars = 9
const val MaxNums = 9
const val TimeLimit = 10

enum class GameStatus {
    Active,
    Won,
    Lost
}

enum class Status(val color: Color) {
    Available(Color.lightGray),
    Used(Color.lightGreen),
    Wrong(Color.lightCoral),
    Candidate(Color.deepSkyBlue)
}

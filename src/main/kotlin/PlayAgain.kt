


import kotlinx.css.Color
import kotlinx.css.color
import kotlinx.html.classes
import kotlinx.html.js.onClickFunction
import react.*
import react.dom.button
import react.dom.div
import styled.css
import styled.styledDiv


interface PlayAgainProps : RProps {
    var gameWon: Boolean
    var onClick: () -> Unit
}

fun RBuilder.playAgain(handler: PlayAgainProps.() -> Unit): ReactElement {
    return child(PlayAgain::class) {
        this.attrs(handler)
    }
}

class PlayAgain(props: PlayAgainProps) : RComponent<PlayAgainProps, RState>(props) {
    override fun RBuilder.render() {
        div(classes = "game-done") {
            styledDiv {
                + if (props.gameWon) "You Win" else "Game Over"
                css {
                    color = if (props.gameWon) Color.green else Color.red
                }
                attrs {
                    classes = setOf("message")
                }
            }
            button {
                +"Play Again"
                attrs {
                    onClickFunction = {
                        props.onClick()
                    }
                }
            }
        }
    }
}
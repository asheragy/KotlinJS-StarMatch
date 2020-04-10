import kotlinx.html.classes
import kotlinx.html.js.onClickFunction
import react.*
import react.dom.button
import react.dom.jsStyle


fun RBuilder.playNumber(handler: PlayNumberProps.() -> Unit): ReactElement {
    return child(PlayNumber::class) {
        this.attrs(handler)
    }
}

interface PlayNumberProps : RProps {
    var number: Int
    var status: (Int) -> (Status)
    var onClick: (Int) -> Unit
}

class PlayNumber(props: PlayNumberProps) : RComponent<PlayNumberProps, RState>(props) {
    override fun RBuilder.render() {
        button {
            +props.number.toString()

            attrs.jsStyle {
                backgroundColor = props.status(props.number).color
            }
            attrs {
                classes = setOf("number")
                onClickFunction = {
                    props.onClick(props.number)
                }
            }
        }
    }

}


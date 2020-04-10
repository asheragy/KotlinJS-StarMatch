import react.*
import react.dom.div


interface StarsDisplayProps : RProps {
    var count: Int
}

fun RBuilder.starsDisplay(handler: StarsDisplayProps.() -> Unit): ReactElement {
    return child(StarsDisplay::class) {
        this.attrs(handler)
    }
}

class StarsDisplay(props: StarsDisplayProps) : RComponent<StarsDisplayProps, RState>(props) {
    override fun RBuilder.render() {
        Utils.range(1, props.count).map { starId ->
            div(classes = "star") {
                key = starId.toString()
            }
        }
    }

}
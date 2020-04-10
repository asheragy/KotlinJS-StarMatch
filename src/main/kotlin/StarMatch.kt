import react.*


interface StarMatchState : RState {
    var gameId: Int
}

class StarMatch : RComponent<RProps, StarMatchState>() {

    override fun StarMatchState.init() {
        gameId = 1
    }

    override fun RBuilder.render() {
        game {
            key = state.gameId.toString()
            startNewGame = {
                setState {
                    gameId += 1
                }
            }
        }
    }
}
import react.*
import react.dom.div
import kotlin.browser.window

interface GameState : RState {
    var availableNums: MutableList<Int>
    var candidateNums: MutableList<Int>
    var stars: Int
    var secondsLeft: Int
}

interface GameProps : RProps {
    var startNewGame: () -> Unit
}

fun RBuilder.game(handler: GameProps.() -> Unit): ReactElement {
    return child(Game::class) {
        this.attrs(handler)
    }
}

fun GameState.setCandidates(newCandidateNums: List<Int>) {
    if (Utils.sum(newCandidateNums) != stars) {
        candidateNums = newCandidateNums.toMutableList()
    }
    else {
        val newAvailableNums = availableNums.filter { !newCandidateNums.contains(it) }
        stars = Utils.randomSumIn(newAvailableNums, MaxStars)
        availableNums = newAvailableNums.toMutableList()
        candidateNums.clear()
    }
}


class Game(props: GameProps) : RComponent<GameProps, GameState>(props) {

    private var timerId = window.setInterval( {
        if (state.secondsLeft > 0 && state.availableNums.size > 0) {
            setState {
                secondsLeft--
            }
        }
        else {
            clearTimer()
        }
    }, 1000)

    private fun clearTimer() {
        window.clearInterval(timerId)
    }

    override fun GameState.init(props: GameProps) {
        availableNums = Utils.range(1, MaxNums).toMutableList()
        candidateNums = mutableListOf()
        stars = Utils.random(1, MaxStars)
        secondsLeft = TimeLimit
    }

    private val candidatesAreWrong: Boolean
        get() = Utils.sum(state.candidateNums) > state.stars

    private val gameStatus: GameStatus
        get() {
            return if (state.availableNums.size == 0)
                GameStatus.Won
            else if (state.secondsLeft == 0)
                GameStatus.Lost
            else
                GameStatus.Active
        }

    private fun onNumberClick(number: Int) {
        val currentStatus = numberStatus(number)
        if (currentStatus == Status.Used || state.secondsLeft == 0)
            return

        val newCandidateNums =
            if (currentStatus == Status.Available)
                state.candidateNums.plus(number)
            else
                state.candidateNums.filter { it != number}

        setState {
            setCandidates(newCandidateNums)
        }
    }

    private fun numberStatus(number: Int): Status {
        if (!state.availableNums.contains(number))
            return Status.Used

        if (state.candidateNums.contains(number)) {
            if (candidatesAreWrong)
                return Status.Wrong

            return Status.Candidate
        }

        return Status.Available
    }

    override fun RBuilder.render() {
        div("game") {
            div("help") {
                +"Pick 1 or more numbers that sum to the number of stars"
            }
            div("body") {
                div("left") {
                    if (gameStatus == GameStatus.Active) {
                        starsDisplay {
                            count = state.stars
                        }
                    }
                    else {
                        playAgain {
                            gameWon = gameStatus == GameStatus.Won
                            onClick = {
                                props.startNewGame()
                            }
                        }
                    }
                }

                div("right") {
                    Utils.range(1, MaxNums).map {
                        playNumber {
                            key = it.toString()
                            number = it
                            status = ::numberStatus
                            onClick = ::onNumberClick
                            key = number.toString()
                        }
                    }
                }
            }

            div("timer") {
                + "Time Remaining: ${state.secondsLeft}"
            }
        }
    }
}
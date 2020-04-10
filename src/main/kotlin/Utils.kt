


object Utils {
    fun range(min: Int, max: Int) = (min..max).map { it }

    fun sum(arr: List<Int>) = arr.sum()

    fun random(min: Int, max: Int) = (min..max).shuffled().first()

    fun randomSumIn(arr: List<Int>, max: Int): Int {
        if (arr.isEmpty())
            return 0

        // TODO change to allow more than 2 elements
        val sums = mutableListOf<Int>()

        for (i in arr.indices) {
            for (j in (i+1) until arr.size) {
                val sum = arr[i] + arr[j]
                if (sum <= max)
                    sums.add(arr[i] + arr[j])
            }
        }

        // Single numbers allowed too
        arr.forEach {
            if (!sums.contains(it))
                sums.add(it)
        }

        return sums.shuffled().first()
    }
}
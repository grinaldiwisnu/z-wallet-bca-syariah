package id.grinaldi.zwallet.utils

import id.grinaldi.zwallet.model.TopUp

class TopUpStep {
    companion object {
        private val listStep = mutableListOf<TopUp>()

        fun getStep(): List<TopUp> {
            listStep.add(TopUp(
                1,
                "Go to the nearest ATM or you can \n" +
                        "use E-Banking."
            ))
            listStep.add(TopUp(
                2,
                "Type your security number on the\n" +
                        "ATM or E-Banking."
            ))
            listStep.add(TopUp(3,
                "Select “Transfer” in the menu"))
            listStep.add(TopUp(
                4,
                "Type the virtual account number that\n" +
                        "we provide you at the top."
            ))
            listStep.add(TopUp(
                5,
                "Type the amount of the money you\n" +
                        "want to top up."
            ))
            listStep.add(TopUp(
                6,
                "Read the summary details"
            ))
            listStep.add(TopUp(
                7,
                "Press transfer / top up"
            ))
            listStep.add(TopUp(
                8,
                "You can see your money in Zwallet\n" +
                        "within 3 hours."
            ))

            return listStep
        }
    }
}
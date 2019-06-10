package hr.fer.edugame.ui.numbers

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import hr.fer.edugame.R
import hr.fer.edugame.extensions.setThrottlingClickListener
import hr.fer.edugame.ui.shared.adapters.NumbersListAdapter
import hr.fer.edugame.ui.shared.adapters.OperationsListAdapter
import hr.fer.edugame.ui.shared.base.BaseFragment
import hr.fer.edugame.ui.shared.base.BasePresenter
import hr.fer.edugame.ui.shared.listeners.HomeListener
import kotlinx.android.synthetic.main.fragment_numbers.calculate
import kotlinx.android.synthetic.main.fragment_numbers.destroyBtn
import kotlinx.android.synthetic.main.fragment_numbers.divide
import kotlinx.android.synthetic.main.fragment_numbers.firstOperand
import kotlinx.android.synthetic.main.fragment_numbers.givenNumbersRecycle
import kotlinx.android.synthetic.main.fragment_numbers.minus
import kotlinx.android.synthetic.main.fragment_numbers.operator
import kotlinx.android.synthetic.main.fragment_numbers.plus
import kotlinx.android.synthetic.main.fragment_numbers.progressBar
import kotlinx.android.synthetic.main.fragment_numbers.resetBtn
import kotlinx.android.synthetic.main.fragment_numbers.secondOperand
import kotlinx.android.synthetic.main.fragment_numbers.times
import kotlinx.android.synthetic.main.fragment_numbers.usedOperationsRecycle
import kotlinx.android.synthetic.main.fragment_numbers.wantedNumber
import kotlinx.android.synthetic.main.view_navigation.backBtn
import kotlinx.android.synthetic.main.view_navigation.navigationTitle
import kotlinx.android.synthetic.main.view_navigation.nextBtn
import javax.inject.Inject

class NumbersFragment : BaseFragment(), NumbersView {

    companion object {
        fun newInstance() = NumbersFragment()
    }

    override val layoutRes: Int = R.layout.fragment_numbers
    override fun providePresenter(): BasePresenter? = presenter

    @Inject
    lateinit var presenter: NumbersPresenter
    private lateinit var homeListener: HomeListener
    lateinit var givenNumbersAdapter: NumbersListAdapter
    lateinit var operationsAdapter: OperationsListAdapter
    private var dialog: AlertDialog? = null

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is HomeListener) {
            homeListener = context
        } else {
            throw RuntimeException(activity?.localClassName + " must implement " + HomeListener::class.java.name)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
        givenNumbersAdapter = NumbersListAdapter {
            onNumberClick(it)
        }
        presenter.init()
        presenter.startCountdown()
    }

    private fun initUI() {
        setOnOperatorsClickListener()
        setOnOperandClickListener()
        initOperationsAdapter()
        nextBtn.setThrottlingClickListener {
            presenter.handleOoNextLevelClick()
        }
        backBtn.setThrottlingClickListener {
            activity?.onBackPressed()
        }
        navigationTitle.setText(R.string.numbers)
        resetBtn.setThrottlingClickListener {
            presenter.reset()
        }

        destroyBtn.setThrottlingClickListener {
            onDestroyClicked()
        }
        calculate.setThrottlingClickListener {
            if (getFirstOperand() != null && getSecondOperand() != null && getOperation() != null) {
                presenter.handleOnCalculateClicked(
                    getFirstOperand() ?: 0,
                    getSecondOperand() ?: 0,
                    getOperation() ?: ""
                )
            }
        }
    }


    private fun initOperationsAdapter() {
        operationsAdapter = OperationsListAdapter()
        usedOperationsRecycle.adapter = operationsAdapter
    }

    private fun onDestroyClicked() {
        if (firstOperand.text.isNotEmpty()) {
            givenNumbersAdapter.updateItem(firstOperand.text.toString().toInt())
        }
        if (secondOperand.text.isNotEmpty()) {
            givenNumbersAdapter.updateItem(secondOperand.text.toString().toInt())
        }

        firstOperand.text = ""
        secondOperand.text = ""
        operator.text = ""
    }

    private fun setOnOperandClickListener() {
        firstOperand.setThrottlingClickListener {
            if(firstOperand.text.isNotEmpty()) {
                givenNumbersAdapter.updateItem(firstOperand.text.toString().toInt())
                firstOperand.text = ""
            }
        }
        secondOperand.setThrottlingClickListener {
            if(secondOperand.text.isNotEmpty()) {
                givenNumbersAdapter.updateItem(secondOperand.text.toString().toInt())
                secondOperand.text = ""
            }
        }
    }

    private fun setOnOperatorsClickListener() {
        plus.setThrottlingClickListener {
            onOperatorClick(plus.text.toString())
        }
        minus.setThrottlingClickListener {
            onOperatorClick(minus.text.toString())
        }
        times.setThrottlingClickListener {
            onOperatorClick(times.text.toString())
        }
        divide.setThrottlingClickListener {
            onOperatorClick(divide.text.toString())
        }
    }

    override fun startLevel(totalPoints: Int, wanted: Int, givenNumbers: List<Int>) {
        wantedNumber?.let {
            wantedNumber.text = wanted.toString()
            givenNumbersAdapter.updateItems(givenNumbers)
            operationsAdapter.resetAdapter()
            givenNumbersRecycle.adapter = givenNumbersAdapter
            navigationTitle.text = String.format(getString(R.string.points), totalPoints.toString())
        }
    }

    override fun resetLevel(wanted: Int, givenNumbers: List<Int>) {
        wantedNumber.text = wanted.toString()
        givenNumbersAdapter.initNewItems(givenNumbers)
        firstOperand.text = ""
        secondOperand.text = ""
        operator.text = ""
        operationsAdapter.resetAdapter()
    }

    override fun updateGivenNumber(number: Int) {
        givenNumbersAdapter.updateItem(number)
        firstOperand.text = ""
        secondOperand.text = ""
        operator.text = ""
    }

    override fun updateOperationsList(operation: String) {
        operationsAdapter.updateItem(operation)
    }

    override fun navigateToNextLevel(points: Int, ownResult: Int, opponentResult: Int) {
        dialog = AlertDialog.Builder(requireContext())
            .setMessage(String.format(getString(R.string.result_numbers), ownResult, opponentResult, points))
            .setPositiveButton(R.string.ok)
            { _, _ ->
                homeListener.onNavigateToLetters()
            }
            .create()
        dialog?.let {
            it.setCancelable(false)
            it.show()
        }
    }

    override fun navigateToNextLevel(points: Int, result: Int) {
        dialog = AlertDialog.Builder(requireContext())
            .setMessage(String.format(getString(R.string.result_numbers_single_player), result, points))
            .setPositiveButton(R.string.ok)
            { _, _ ->
                homeListener.onNavigateToLetters()
            }
            .create()

        dialog?.let {
            it.setCancelable(false)
            it.show()
        }
    }

    private fun onOperatorClick(operation: String) {
        operator.text = operation
    }

    private fun onNumberClick(number: String) {
        if (firstOperand.text.isEmpty()) {
            firstOperand.text = number
        } else {
            if (number.toInt() != 0) {
                secondOperand.text = number
            }
        }
    }

    fun getFirstOperand(): Int? {
        if (firstOperand.text.isNotEmpty()) {
            return firstOperand.text.toString().toInt()
        } else {
            Toast.makeText(context, R.string.enter_all_data, Toast.LENGTH_SHORT).show()
        }
        return null
    }

    fun getOperation(): String? {
        if (operator.text.isNotEmpty()) {
            return operator.text.toString()
        } else {
            Toast.makeText(context, R.string.enter_all_data, Toast.LENGTH_SHORT).show()
        }
        return null
    }

    fun getSecondOperand(): Int? {
        if (secondOperand.text.isNotEmpty()) {
            return secondOperand.text.toString().toInt()
        } else {
            Toast.makeText(context, R.string.enter_all_data, Toast.LENGTH_SHORT).show()
        }
        return null
    }

    override fun updateProgress(secondsRemaining: Long) {
        progressBar.progress = secondsRemaining.toInt()
    }

    override fun onStop() {
        dialog?.let {
            if (it.isShowing) {
                it.cancel()
            }
        }
        super.onStop()
    }

    override fun onDestroy() {
        presenter.onDestroy()
        super.onDestroy()
    }

    override fun setGoBack(goBack: Boolean) {
        homeListener.goBack(goBack)
    }
}
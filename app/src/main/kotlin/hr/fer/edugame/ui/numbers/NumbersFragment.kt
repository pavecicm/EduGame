package hr.fer.edugame.ui.numbers

import android.os.Bundle
import android.view.View
import android.widget.Toast
import hr.fer.edugame.R
import hr.fer.edugame.extensions.setThrottlingClickListener
import hr.fer.edugame.ui.shared.adapters.NumbersListAdapter
import hr.fer.edugame.ui.shared.adapters.OperationsListAdapter
import hr.fer.edugame.ui.shared.base.BaseFragment
import hr.fer.edugame.ui.shared.base.BasePresenter
import kotlinx.android.synthetic.main.fragment_numbers.calculate
import kotlinx.android.synthetic.main.fragment_numbers.destroyBtn
import kotlinx.android.synthetic.main.fragment_numbers.divide
import kotlinx.android.synthetic.main.fragment_numbers.firstOperand
import kotlinx.android.synthetic.main.fragment_numbers.givenNumbersRecycle
import kotlinx.android.synthetic.main.fragment_numbers.minus
import kotlinx.android.synthetic.main.fragment_numbers.operator
import kotlinx.android.synthetic.main.fragment_numbers.plus
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
    lateinit var givenNumbersAdapter: NumbersListAdapter
    lateinit var operationsAdapter: OperationsListAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
        presenter.init()
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
            givenNumbersAdapter.updateItem(firstOperand.text.toString().toInt())
            firstOperand.text = ""
        }
        secondOperand.setThrottlingClickListener {
            givenNumbersAdapter.updateItem(secondOperand.text.toString().toInt())
            secondOperand.text = ""
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

    override fun startLevel(wanted: Int, givenNumbers: List<Int>) {
        wantedNumber.text = wanted.toString()
        givenNumbersAdapter = NumbersListAdapter(givenNumbers.toMutableList()) {
            onNumberClick(it)
        }
        givenNumbersRecycle.adapter = givenNumbersAdapter
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

    override fun navigateToNextLevel(wanted: Int, numbers: List<Int>) {
        Toast.makeText(context, String.format(getString(R.string.congrats), 45), Toast.LENGTH_SHORT).show()
        resetLevel(wanted = wanted, givenNumbers = numbers)
    }

    private fun onOperatorClick(operation: String) {
        operator.text = operation
    }

    private fun onNumberClick(number: String) {
        if (firstOperand.text.isEmpty()) {
            firstOperand.text = number
        } else {
            if(number.toInt() != 0) {
                secondOperand.text = number
            }
        }
        givenNumbersAdapter.destroyItem(number.toInt())
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
}
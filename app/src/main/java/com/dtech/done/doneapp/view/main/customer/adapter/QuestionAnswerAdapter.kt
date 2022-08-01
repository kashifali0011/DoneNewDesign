package com.dtech.done.doneapp.view.main.customer.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dtech.done.doneapp.R
import com.dtech.done.doneapp.data.model.OptionIndexModel
import com.dtech.done.doneapp.data.model.responsemodel.Optiontree
import com.dtech.done.doneapp.data.model.responsemodel.Questiontree
import com.dtech.done.doneapp.databinding.AdapterQuestionAnswerBinding


class QuestionAnswerAdapter(
    val context: Context,
    private var questionTree: ArrayList<Questiontree> = ArrayList(),
    var optionsText: String = ""
) : RecyclerView.Adapter<QuestionAnswerAdapter.ViewHolder>() {

    var questionId = ""
    var optionId: Int = 0
    private val optionIds: ArrayList<OptionIndexModel> = ArrayList()
    private lateinit var optionIndexModel: OptionIndexModel
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var binding: AdapterQuestionAnswerBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.adapter_question_answer, parent, false)
        return ViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: ViewHolder, @SuppressLint("RecyclerView") position: Int) {
        if (questionTree != null && questionTree!!.size > 0) {
            for (i in questionTree!!.indices) {
                if (questionTree!![position].optiontree[0].id != 0) {
                    val optionTree = Optiontree()
                    optionTree.option = optionsText
                    questionTree!![position].optiontree.add(0, optionTree)
                }
            }
        }
        holder.binding.model = questionTree[position]

        //     binding.tvType.text = questionTree!![position].questionTitle
        val adapter: ArrayAdapter<String> = ArrayAdapter(
            context, android.R.layout.simple_spinner_item,
            questionTree!![position].optiontree.map { it.option!! })
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        holder.binding.spinner.adapter = adapter

        holder.binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, pos: Int, p3: Long) {

                val manager = LinearLayoutManager(context)
                manager.orientation = RecyclerView.VERTICAL
                holder.binding.questionTreeSpinner.layoutManager = manager
                optionIndexModel = OptionIndexModel(optionId, questionId)

                if (questionTree[position].optionId == 0) {
                    questionTree[position].parentId = "0"
                    optionIndexModel.questionId = questionTree[position].questionId.toString()
                }
                else {
                    if (questionTree[position].parentId != "0") {
                        questionTree[position].parentId =
                            questionTree[position].questionId.toString()
                    } else {
                        if (!questionTree[position].parentId.contains(
                                java.lang.String.valueOf(
                                    questionTree[position].questionId
                                )
                            )
                        ) {
                            questionTree[position].parentId =
                                questionTree[position].parentId
                                    .toString() + "," + questionTree[position].questionId
                        } else {
                            questionTree[position].parentId = questionTree[position].parentId
                        }
                    }
                    optionIndexModel.questionId = questionTree[position].parentId
                }
                optionIndexModel.optionId = questionTree[position].optiontree[pos].id!!
                if (!questionTree[position].optiontree[pos].questiontree.isNullOrEmpty()) {
                    for (j in 0 until questionTree[position].optiontree[pos].questiontree.size) {
                        var containParentId = false
                        val parts: Array<String> =
                            questionTree[position].parentId.split(",").toTypedArray()
                        for (part in parts) {
                            if (part.equals(java.lang.String.valueOf(questionTree[position].questionId), ignoreCase = true)) {
                                containParentId = true
                            }
                        }
                        if (!containParentId) {
                            questionTree[position].optiontree[pos].questiontree[position].parentId = questionTree[position].parentId + "," + questionTree[position].questionId
                        } else {
                            questionTree[position].optiontree[pos].questiontree[position].parentId = questionTree[position].parentId

                        }
                    }
                }
                val indexesToRemove = java.util.ArrayList<Int>()
                for (z in optionIds.indices) {
                    val parts: Array<String> = arrayOf(optionIds[z].questionId.split(",").toString())
                    for (part in parts) {
                        if (part.equals(java.lang.String.valueOf(questionTree[position].questionId), ignoreCase = true)) {
                            indexesToRemove.add(z)
                        }
                    }
                }
                val tempList: java.util.ArrayList<OptionIndexModel> =
                    object : java.util.ArrayList<OptionIndexModel>() {}
                for (i in indexesToRemove.indices) {
                    tempList.add(optionIds[indexesToRemove[i]])
                }

                if (tempList.size != 0) {
                    for (a in tempList.indices) {
                        optionIds.remove(tempList[a])
                    }
                }
                for (i in optionIds.indices) {
                    if (optionIds[i].questionId.equals(optionIndexModel.questionId, ignoreCase = true)) {
                        optionIds.removeAt(i)
                        break
                    }
                }
                optionIds.add(optionIndexModel)
                if (!optionIds.contains(optionIndexModel)) {
                    optionIds.add(optionIndexModel)
                }

           /*     if (optionIds.size > 0) {
                    for (i in optionIds.indices) {
                        Log.d("QAList", "QuestionId(" + i + ") = " + optionIds[i].questionId)
                        Log.d("QAList", "OptionId(" + i + ") = " + optionIds[i].optionId)
                    }
                } else {
                    Log.d("QAList", "List Empty")
                }*/
                val adapter: QuestionAnswerAdapter = QuestionAnswerAdapter(context, questionTree[position].optiontree[pos].questiontree, optionsText)

                holder.binding.questionTreeSpinner.adapter = adapter

                if (questionTree[position].optiontree[pos].id == 0) {
                    Log.d("position", "Gone$position")
                    holder.binding.questionTreeSpinner.visibility = View.GONE
                } else {
                    holder.binding.questionTreeSpinner.visibility = View.VISIBLE
                    Log.d("position", "Visible$position")
                }


            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }

        }


    }

    override fun getItemCount(): Int {
        return questionTree!!.size
    }

    class ViewHolder(var view: View) : RecyclerView.ViewHolder(view){
        val binding : AdapterQuestionAnswerBinding = DataBindingUtil.bind(view)!!
    }
}
package com.morimori.toyamabenquizu;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import android.content.Intent;

public class QuestionActivity extends Activity
{
	QuestionDat questionData;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_question);


		// Intent を取得する
		Intent intent = getIntent();

		questionData = (QuestionDat)intent.getSerializableExtra("Question");


		((TextView)findViewById(R.id.questionNoLabel)).setText(QuestionDataManager.sharedInstance.nowQuestionIndex + "/10");


	}
}

package com.morimori.toyamabenquizu;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.content.Intent;

import org.w3c.dom.Text;

public class QuestionActivity extends Activity
{
	QuestionDat questionData;
	TextView questionTxt;
	TextView questionExampleTextView;

	Button answer1Button;      // 選択肢1ボタン
	Button answer2Button;      // 選択肢2ボタン
	Button answer3Button;    // 選択肢3ボタン
	Button answer4Button;      // 選択肢4ボタン



	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_question);

		// Intent を取得する
		Intent intent = getIntent();
		questionData = (QuestionDat)intent.getSerializableExtra("Question");

		((TextView)findViewById(R.id.questionNoLabel)).setText(QuestionDataManager.sharedInstance.nowQuestionIndex + "/10");

		questionTxt = (TextView)findViewById(R.id.questionTextView);
		questionTxt.setText(questionData.question);


		questionExampleTextView = (TextView)findViewById(R.id.questionExampleTextView);
		questionExampleTextView.setText(questionData.questionExample);


		answer1Button = (Button)findViewById(R.id.answer1Button);
		answer1Button.setText(" 1 " + questionData.answer1);

		answer2Button = (Button)findViewById(R.id.answer2Button);
		answer2Button.setText(" 2 " + questionData.answer2);

		answer3Button = (Button)findViewById(R.id.answer3Button);
		answer3Button.setText(" 3 " + questionData.answer3);

		answer4Button = (Button)findViewById(R.id.answer4Button);
		answer4Button.setText(" 4 " + questionData.answer4);


	}
}

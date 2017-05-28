package com.morimori.toyamabenquizu;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.content.Intent;

public class MainActivity extends Activity implements View.OnClickListener
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		((Button)findViewById(R.id.startButton)).setOnClickListener(this);
	}


	@Override
	public void onClick(View v)
	{
		QuestionDataManager.sharedInstance.loadQuestion(this, "toyamaben.csv");


		// 問題文のセット
		QuestionDat questionData = QuestionDataManager.sharedInstance.nextQuestion();

		Intent intent = new Intent(MainActivity.this, QuestionActivity.class);
		intent.putExtra("Question", questionData);
		startActivity(intent);

//		Intent intent = new Intent(MainActivity.this, ResultActivity.class);
//		startActivity(intent);
	}
}

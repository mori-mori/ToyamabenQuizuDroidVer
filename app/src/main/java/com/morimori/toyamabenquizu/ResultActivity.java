package com.morimori.toyamabenquizu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.app.Activity;

/**
 * 結果表示用クラス
 */
public class ResultActivity extends Activity implements View.OnClickListener
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_result);

		Button backButton = (Button)findViewById(R.id.backHomeButton);
		backButton.setOnClickListener(this);

		int questionCount = 10;

		int correctCount = 0;

		for (QuestionDat questionData : QuestionDataManager.sharedInstance.questionDataArray)
		{
			if (questionData.isCorrect())
			{
				correctCount += 1;
			}
		}

		float correctPercent = ((float)correctCount / (float)questionCount * 100);

		((TextView)findViewById(R.id.correctPercentLabel)).setText(String.format("正解率%.0f%%",correctPercent));
	}

	@Override
	public void onClick(View v)
	{
		Intent intent = new Intent(ResultActivity.this, MainActivity.class);
		startActivity(intent);
	}
}

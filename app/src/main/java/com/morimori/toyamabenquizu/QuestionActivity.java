package com.morimori.toyamabenquizu;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.content.Intent;
import android.widget.ProgressBar;
import android.os.CountDownTimer;
import 	android.view.animation.AlphaAnimation;
import android.view.animation.Animation.AnimationListener;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;
import 	java.io.InputStream;
import java.io.IOException;
import android.view.animation.Animation;


public class QuestionActivity extends Activity implements View.OnClickListener, AnimationListener
{
	QuestionDat questionData;
	TextView questionTxt;
	TextView questionExampleTextView;

	Button answer1Button;      // 選択肢1ボタン
	Button answer2Button;      // 選択肢2ボタン
	Button answer3Button;    // 選択肢3ボタン
	Button answer4Button;      // 選択肢4ボタン
	Button backHomeButton;
	ProgressBar progBar;
	CountDown countDown;

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


		answer1Button = (Button)findViewById(R.id.answer1Button);
		findViewById(R.id.answer1Button).setOnClickListener(this);

		answer2Button = (Button)findViewById(R.id.answer2Button);
		answer2Button.setOnClickListener(this);

		answer3Button = (Button)findViewById(R.id.answer3Button);
		answer3Button.setOnClickListener(this);

		answer4Button = (Button)findViewById(R.id.answer4Button);
		answer4Button.setOnClickListener(this);

		backHomeButton = (Button)findViewById(R.id.backHomeButton);
		backHomeButton.setOnClickListener(this);


		progBar = (ProgressBar)findViewById(R.id.timeProgressBar);

		// 最大値を設定する.
		progBar.setMax(10);
		// プログレスバーの値を設定する.
		progBar.setProgress(10);

		countDown = new CountDown(10000, 1000);
		countDown.start();




		ImageView correctImageView = (ImageView)findViewById(R.id.correctImageView);
		ImageView incorrectImageView = (ImageView)findViewById(R.id.incorrectImageView);

		try
		{
			InputStream correctStream = getResources().getAssets().open("correct.png");
			Bitmap correctBitmap = BitmapFactory.decodeStream(correctStream);
			correctImageView.setImageBitmap(correctBitmap);

			InputStream incorrectStream = getResources().getAssets().open("incorrect.png");
			Bitmap incorrectBitmap = BitmapFactory.decodeStream(incorrectStream);
			incorrectImageView.setImageBitmap(incorrectBitmap);
		}
		catch (IOException e)
		{
		}


	}

	@Override
	public void onClick(View v)
	{
		switch (v.getId())
		{
			case R.id.answer1Button:
				questionData.userChoiceAnswerNumber = 1;

				QuestionDataManager.sharedInstance.questionDataArray.remove(questionData.questionNo - 1);
				QuestionDataManager.sharedInstance.questionDataArray.add(questionData);

				goNextQuestionWithAnimation();
				break;

			case R.id.answer2Button:
				questionData.userChoiceAnswerNumber = 2;

				QuestionDataManager.sharedInstance.questionDataArray.remove(questionData.questionNo - 1);
				QuestionDataManager.sharedInstance.questionDataArray.add(questionData);

//				progBar.setProgress(10);
				goNextQuestionWithAnimation();
				break;

			case R.id.answer3Button:
				questionData.userChoiceAnswerNumber = 3;

				QuestionDataManager.sharedInstance.questionDataArray.remove(questionData.questionNo - 1);
				QuestionDataManager.sharedInstance.questionDataArray.add(questionData);

//				progBar.setProgress(10);
				goNextQuestionWithAnimation();
				break;

			case R.id.answer4Button:
				questionData.userChoiceAnswerNumber = 4;

				QuestionDataManager.sharedInstance.questionDataArray.remove(questionData.questionNo - 1);
				QuestionDataManager.sharedInstance.questionDataArray.add(questionData);

//				progBar.setProgress(10);
				goNextQuestionWithAnimation();
				break;

			case R.id.backHomeButton:
				Intent intent = new Intent(QuestionActivity.this, MainActivity.class);
				startActivity(intent);
				break;
		}
	}

	private void goNextQuestionWithAnimation()
	{
		if (questionData.isCorrect())
		{
			goNextQuestionWithCorrectAnimation();
		}
		else
		{
			goNextQuestionWithIncorrectAnimation();
		}
	}

	private void goNextQuestionWithCorrectAnimation()
	{
//		AlphaAnimation alpha = new AlphaAnimation(
//				0.0f,  // 開始時の透明度（0は完全に透過）
//				1.0f); // 終了時の透明度（1は全く透過しない）
//
//		// 3秒かけてアニメーションする
//		alpha.setDuration(3000);
//
//		// アニメーション終了時の表示状態を維持する
//		alpha.setFillEnabled(true);
//		alpha.setFillAfter  (true);
		countDown.cancel();

		findViewById(R.id.correctImageView).setAlpha(1.0f);
		AlphaAnimation aa = new AlphaAnimation(0.0f, 1.0f);
		aa.setDuration(3000);
		aa.setFillEnabled(true);
		aa.setFillAfter(true);
		findViewById(R.id.correctImageView).startAnimation(aa);

		aa.setAnimationListener(this);

//		goNextQuestion();
	}


	private void goNextQuestionWithIncorrectAnimation()
	{
		countDown.cancel();

		findViewById(R.id.incorrectImageView).setAlpha(1.0f);
		AlphaAnimation aa = new AlphaAnimation(0.0f, 1.0f);
		aa.setDuration(3000);
		aa.setFillEnabled(true);
		aa.setFillAfter(true);
		findViewById(R.id.incorrectImageView).startAnimation(aa);

		aa.setAnimationListener(this);

		//		AlphaAnimation alpha = new AlphaAnimation(
//				0.0f,  // 開始時の透明度（0は完全に透過）
//				1.0f); // 終了時の透明度（1は全く透過しない）
//
//		// 3秒かけてアニメーションする
//		alpha.setDuration(3000);
//
//		// アニメーション終了時の表示状態を維持する
//		alpha.setFillEnabled(true);
//		alpha.setFillAfter  (true);
//
		// アニメーションを開始
//		findViewById(R.id.incorrectImageView).startAnimation(alpha);
//		AlphaAnimation aa = new AlphaAnimation(0, 1);
//		aa.setDuration(3000);
//		aa.setFillAfter(true);
//		findViewById(R.id.incorrectImageView).startAnimation(aa);

//		goNextQuestion();
	}

	private void goNextQuestion()
	{
		QuestionDat nextQuestion = QuestionDataManager.sharedInstance.nextQuestion();
		if (nextQuestion != null)
		{
			Intent intent = new Intent(QuestionActivity.this, QuestionActivity.class);
			intent.putExtra("Question", nextQuestion);
			startActivity(intent);
		}
		else
		{
			Intent intent = new Intent(QuestionActivity.this, ResultActivity.class);
			startActivity(intent);
		}
	}

	@Override
	public void onAnimationEnd(Animation animation)
	{
		goNextQuestion();
	}

	@Override
	public void onAnimationRepeat(Animation animation)
	{
	}

	@Override
	public void onAnimationStart(Animation animation)
	{
	}



	class CountDown extends CountDownTimer
	{
		public CountDown(long millisInFuture, long countDownInterval)
		{
			super(millisInFuture, countDownInterval);
		}

		@Override
		public void onFinish()
		{
			 progBar = (ProgressBar)findViewById(R.id.timeProgressBar);

			questionData.userChoiceAnswerNumber = 99;

			QuestionDataManager.sharedInstance.questionDataArray.remove(questionData.questionNo - 1);
			QuestionDataManager.sharedInstance.questionDataArray.add(questionData);

//			progBar.setProgress(10);
			countDown.cancel();

			goNextQuestionWithAnimation();
		}

		// インターバルで呼ばれる
		@Override
		public void onTick(long millisUntilFinished)
		{
			questionTxt = (TextView)findViewById(R.id.questionTextView);
//			questionTxt.setText(Long.toString(millisUntilFinished / 1000));

			String test = Long.toString(millisUntilFinished / 1000);
			progBar = (ProgressBar)findViewById(R.id.timeProgressBar);
			progBar.setProgress(Integer.parseInt(test));
		}
	}
}

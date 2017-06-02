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
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.KeyEvent;

/**
 * 問題表示・回答画面
 */
public class QuestionActivity extends Activity implements View.OnClickListener, AnimationListener
{
	private QuestionDat questionData;          // 問題データ
	private ProgressBar answerCountBar;        // 回答時間タイマー
	private CountDown countDown;               // タイマークラス

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_question);

		// 前の画面からQuestionDateオブジェクトを受け取る
		questionData = (QuestionDat)getIntent().getSerializableExtra("Question");

		((TextView)findViewById(R.id.questionNoLabel)).setText(QuestionDataManager.sharedInstance.nowQuestionIndex + "/10");
		((TextView)findViewById(R.id.questionTextView)).setText(questionData.question);
		((TextView)findViewById(R.id.questionExampleTextView)).setText(questionData.questionExample);

		// answer1Button set
		Button answer1Button = (Button)findViewById(R.id.answer1Button);
		answer1Button.setText(" 1 " + questionData.answer1);
		answer1Button.setOnClickListener(this);

		// answer2Button set
		Button answer2Button = (Button)findViewById(R.id.answer2Button);
		answer2Button.setText(" 2 " + questionData.answer2);
		answer2Button.setOnClickListener(this);

		// answer3Button set
		Button answer3Button = (Button)findViewById(R.id.answer3Button);
		answer3Button.setText(" 3 " + questionData.answer3);
		answer3Button.setOnClickListener(this);

		// answer4Button set
		Button answer4Button = (Button)findViewById(R.id.answer4Button);
		answer4Button.setText(" 4 " + questionData.answer4);
		answer4Button.setOnClickListener(this);

		// backHomeButton set
		Button backHomeButton = (Button)findViewById(R.id.backHomeButton);
		backHomeButton.setOnClickListener(this);

		answerCountBar = (ProgressBar)findViewById(R.id.timeProgressBar);

		// 最大値を設定
		answerCountBar.setMax(100);
		// プログレスバーの値を設定
		answerCountBar.setProgress(100);

		// タイマー作成
		countDown = new CountDown(10000, 100);
		countDown.start();

		ImageView correctImageView = (ImageView)findViewById(R.id.correctImageView);
		ImageView incorrectImageView = (ImageView)findViewById(R.id.incorrectImageView);

		try
		{
			// 正解画像読み込み
			InputStream correctStream = getResources().getAssets().open("correct.png");
			Bitmap correctBitmap = BitmapFactory.decodeStream(correctStream);
			correctImageView.setImageBitmap(correctBitmap);

			// 不正解画像読み込み
			InputStream incorrectStream = getResources().getAssets().open("incorrect.png");
			Bitmap incorrectBitmap = BitmapFactory.decodeStream(incorrectStream);
			incorrectImageView.setImageBitmap(incorrectBitmap);
		}
		catch (IOException e)
		{
			AlertDialog.Builder alertDlg = new AlertDialog.Builder(this);
			alertDlg.setTitle("エラー");
			alertDlg.setMessage("画像読み込みに失敗しました。");
			alertDlg.setPositiveButton("OK", new DialogInterface.OnClickListener()
			{
				public void onClick(DialogInterface dialog, int which)
				{
					// OK ボタンクリック処理
				}
			});
			alertDlg.create().show();
		}
	}

	@Override
	public void onClick(View v)
	{
		((Button)findViewById(R.id.answer1Button)).setEnabled(false);
		((Button)findViewById(R.id.answer2Button)).setEnabled(false);
		((Button)findViewById(R.id.answer3Button)).setEnabled(false);
		((Button)findViewById(R.id.answer4Button)).setEnabled(false);

		switch (v.getId())
		{
			case R.id.answer1Button:
				questionData.userChoiceAnswerNumber = 1;
				break;

			case R.id.answer2Button:
				questionData.userChoiceAnswerNumber = 2;
				break;

			case R.id.answer3Button:
				questionData.userChoiceAnswerNumber = 3;
				break;

			case R.id.answer4Button:
				questionData.userChoiceAnswerNumber = 4;
				break;

			case R.id.backHomeButton:
				Intent intent = new Intent(QuestionActivity.this, MainActivity.class);
				startActivity(intent);
				return;
		}

		QuestionDataManager.sharedInstance.questionDataArray.remove(questionData.questionNo - 1);
		QuestionDataManager.sharedInstance.questionDataArray.add(questionData);

		goNextQuestionWithAnimation();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		if (keyCode == KeyEvent.KEYCODE_BACK)
		{
			Intent intent = new Intent(QuestionActivity.this, MainActivity.class);
			startActivity(intent);
			return true;
		}
		else
		{
			return false;
		}
	}

	/**
	 * アニメーション後、次の問題表示
	 */
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

	/**
	 * 正解アニメーション
	 */
	private void goNextQuestionWithCorrectAnimation()
	{
		countDown.cancel();

		findViewById(R.id.correctImageView).setAlpha(1.0f);
		AlphaAnimation aa = new AlphaAnimation(0.0f, 1.0f);
		aa.setDuration(3000);
		aa.setFillEnabled(true);
		aa.setFillAfter(true);
		findViewById(R.id.correctImageView).startAnimation(aa);

		aa.setAnimationListener(this);
	}

	/**
	 * 不正解アニメーション
	 */
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
	}

	/**
	 * 次の問題表示
	 */
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
	public void onAnimationRepeat(Animation animation) {}

	@Override
	public void onAnimationStart(Animation animation) {}

	/**
	 * タイマークラス
	 */
	class CountDown extends CountDownTimer
	{
		public CountDown(long millisInFuture, long countDownInterval)
		{
			super(millisInFuture, countDownInterval);
		}

		// インターバル終了時に呼ばれる
		@Override
		public void onFinish()
		{
			answerCountBar = (ProgressBar)findViewById(R.id.timeProgressBar);

			questionData.userChoiceAnswerNumber = 99;

			QuestionDataManager.sharedInstance.questionDataArray.remove(questionData.questionNo - 1);
			QuestionDataManager.sharedInstance.questionDataArray.add(questionData);

			countDown.cancel();

			goNextQuestionWithAnimation();
		}

		// インターバルで呼ばれる
		@Override
		public void onTick(long millisUntilFinished)
		{
			String timer = Long.toString(millisUntilFinished / 100);
			answerCountBar = (ProgressBar)findViewById(R.id.timeProgressBar);
			answerCountBar.setProgress(Integer.parseInt(timer));
		}
	}
}

package com.morimori.toyamabenquizu;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * Created by tatsunori on 2017/05/21.
 */


 class QuestionData
{
	// 問題文
	String question;
	// 例文
	String questionExample;

	// 選択肢1
	String answer1;
	// 選択肢2
	String answer2;
	// 選択肢3
	String answer3;
	// 選択肢4
	String answer4;
	// 正解の番号
	Integer correctAnswerNumber;

	// ユーザが選択した選択肢の番号
	Integer userChoiceAnswerNumber;

	// 問題の番号
	Integer questionNo = 0;

	// クラスが生成された時の処理
	QuestionData(String[] questionSourceDataArray)
	{
		question = questionSourceDataArray[0];
		questionExample = questionSourceDataArray[1];
		answer1 = questionSourceDataArray[2];
		answer2 = questionSourceDataArray[3];
		answer3 = questionSourceDataArray[4];
		answer4 = questionSourceDataArray[5];
		correctAnswerNumber = Integer.parseInt(questionSourceDataArray[6]);
	}

	// ユーザが選択した答えが正解かどうか判定する
	private boolean isCorrect()
	{
		// 答えが一致しているかどうか判定する
		if (correctAnswerNumber == userChoiceAnswerNumber)
		{
			// 正解
			return true;
		}
		// 不正解
		return false;
	}
}


public class QuestionDataManager
{
	private Context context;
	private String file;

	// シングルトンのオブジェクトを作成
	static  QuestionDataManager sharedInstance = new QuestionDataManager();

	// 問題を格納するための配列
	ArrayList<QuestionData> questionDataArray = new ArrayList<QuestionData>();

	// 現在の問題のインデックス
	Integer nowQuestionIndex = 0;

	private QuestionDataManager() {}

	public void loadQuestion(Context context, String file)
	{
		// 格納済みの問題文があれば一旦削除しておく 
		questionDataArray.clear();

		// 現在の問題のインデックスを初期化
		nowQuestionIndex = 0;

		// AssetManagerの呼び出し
		AssetManager assetManager = context.getResources().getAssets();
		try
		{
			// CSVファイルの読み込み
			InputStream is = assetManager.open(file);
			InputStreamReader inputStreamReader = new InputStreamReader(is);
			BufferedReader bufferReader = new BufferedReader(inputStreamReader);
			String line = "";

			while ((line = bufferReader.readLine()) != null)
			{
				// 各行が","で区切られていて5つの項目
				StringTokenizer st = new StringTokenizer(line, ",");
				String first = st.nextToken();
				String second = st.nextToken();
				String third = st.nextToken();
				String fourth = st.nextToken();
				String fifth = st.nextToken();
				String sixth = st.nextToken();
				String seventh = st.nextToken();

				// 問題データを格納するオブジェクトを作成
				QuestionData questionData = new QuestionData(new String[] {first, second, third, fourth, fifth, sixth, seventh});

				// 問題を追加
				questionDataArray.add(questionData);

				// 問題番号を設定
				questionData.questionNo = questionDataArray.size();
			}

			bufferReader.close();

		} catch (IOException e)
		{
			//e.printStackTrace();
//			((TextView)findViewById(R.id.contentsTextView)).setText("読み込みに失敗しました・・・");
		}
	}
}

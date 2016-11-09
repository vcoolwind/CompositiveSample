package vcoolwind.com.compositivesample.ui.task;

import android.os.AsyncTask;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * Created by BlackStone on 2016/11/9.
 */

public class AsyncTaskSample extends AsyncTask<Integer, Integer, String> {
    TextView textView = null;
    ProgressBar progressBar = null;

    boolean pause = false;
    boolean stop = false;

    public AsyncTaskSample(TextView textView, ProgressBar progressBar) {
        this.textView = textView;
        this.progressBar = progressBar;
    }

    /**
     * 这里的Integer参数对应AsyncTask中的第一个参数
     * 这里的String返回值对应AsyncTask的第三个参数
     * 该方法并不运行在UI线程当中，主要用于异步操作，所有在该方法中不能对UI当中的空间进行设置和修改
     * 但是可以调用publishProgress方法触发onProgressUpdate对UI进行操作
     */
    @Override
    protected String doInBackground(Integer... integers) {
        int sleep = integers[0];
        int pos = 0;
        while (!stop) {
            if (!pause) {
                if (pos < 100) {
                    pos += 10;
                    publishProgress(pos);
                    sleep(sleep);
                } else {
                    break;
                }
            } else {
                //等待被激活
                sleep(100);
            }
        }
        return String.valueOf(pos);
    }

    public void stop() {
        stop = true;
    }

    public void pause() {
        pause = true;
    }

    public void resume() {
        pause = false;
    }

    @Override
    protected void onPostExecute(String s) {
        textView.setText("异步操作执行结束:" + s);

    }

    /**
     * 这里的Intege参数对应AsyncTask中的第二个参数
     * 在doInBackground方法当中，，每次调用publishProgress方法都会触发onProgressUpdate执行
     * onProgressUpdate是在UI线程中执行，所有可以对UI空间进行操作
     */
    @Override
    protected void onProgressUpdate(Integer... values) {
        int value = values[0];
        textView.setText("异步操作执行结束:" + value);
        progressBar.setProgress(value);
    }

    @Override
    protected void onPreExecute() {
        textView.setText("开始执行异步线程");
    }

    private void sleep(int sleep) {
        try {
            Thread.sleep(sleep);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

package net.issoa;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;


public class AsyncTemplateActivity extends Activity
{
    public static final String TAG = "AsyncTemplateActivity";

    public ProgressBar progressBar = null;
    public TextView taskStatus = null;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        progressBar = (ProgressBar) findViewById(R.id.loaderProgressBar);
        taskStatus = (TextView) findViewById(R.id.taskStatus);

        ((Button) findViewById(R.id.demoButton1)).setOnClickListener(
            new View.OnClickListener() {
                public void onClick(View v) {
                    int numSteps = 5;

                    progressBar.setMax(numSteps);
                    progressBar.setProgress(0);

                    taskStatus.setText("Running");

                    new AppTask().execute(
                        new AppTask.Payload(
                            AppTask.APPTASK_1,
                            new Object[] { AsyncTemplateActivity.this, 
                                            new Integer(numSteps) }));
                }
            }
        );

        ((Button) findViewById(R.id.demoButton2)).setOnClickListener(
            new View.OnClickListener() {
                public void onClick(View v) {
                    int numSteps = -1;
                    progressBar.setMax(numSteps);
                    progressBar.setProgress(0);

                    taskStatus.setText("Running");

                    new AppTask().execute(
                        new AppTask.Payload(
                            AppTask.APPTASK_1,
                            new Object[] { AsyncTemplateActivity.this, 
                                            new Integer(numSteps) }));
                }
            }
        );
    }
}

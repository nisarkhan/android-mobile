/*
 * Copyright (C) 2009 
 * Jayesh Salvi <jayesh@altcanvas.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.altcanvas.asynctemplate;

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

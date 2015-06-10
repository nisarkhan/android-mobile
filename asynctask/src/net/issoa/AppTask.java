package net.issoa;

import android.os.AsyncTask;

public class AppTask extends AsyncTask<AppTask.Payload, Object, AppTask.Payload>
{
    public static final String TAG = "AppTask";

    public static final int APPTASK_1 = 1001;
    public static final int APPTASK_2 = 1002;

    /*
     * Runs on GUI thread
     */
    @Override
	protected void onPreExecute() {
    }

    /*
     * Runs on GUI thread
     */
    @Override
	public void onPostExecute(AppTask.Payload payload)
    {
        switch(payload.taskType) {

        case APPTASK_1:
            AsyncTemplateActivity app = 
                (AsyncTemplateActivity) payload.data[0];

            if(payload.result != null) {

                // Present the result on success
                int answer = ((Integer) payload.result).intValue();
                app.taskStatus.setText("Success: answer = "+answer);

            } else {
                // Report the exception on failure
                String msg = (payload.exception !=null) ?
                                payload.exception.toString() : "";
                app.taskStatus.setText("Failure: error ="+msg);
            }

            break;

        case APPTASK_2:
            break;
        }
    }

    /*
     * Runs on GUI thread
     */
    @Override
	public void onProgressUpdate(Object... value)
    {
        int type = ((Integer) value[0]).intValue();

        switch(type) {

        case APPTASK_1:
            AsyncTemplateActivity app = (AsyncTemplateActivity) value[1];
            int progress = ((Integer) value[2]).intValue();
            app.progressBar.setProgress(progress);
            break;

        case APPTASK_2:
            break;
        }

    }

    /*
     * Runs on background thread
     */
    @Override
	public AppTask.Payload doInBackground(AppTask.Payload... params)
    {
        AppTask.Payload payload = params[0];

        try {
            switch(payload.taskType) {
            case APPTASK_1:

                // extract the parameters of the task from payload
                AsyncTemplateActivity app = 
                    (AsyncTemplateActivity) payload.data[0];
                int numSteps = ((Integer) payload.data[1]).intValue();

                if(numSteps < 0) throw new AppException("Invalid input");

                // perform the task
                int progress = 0;
                for(; progress < numSteps; progress++) {
                    try {
                        Thread.currentThread();
						// pretend to work for 1 second
                        Thread.sleep(1000); 
                    } catch(InterruptedException ie) {
                        break;
                    }
                    publishProgress(new Object[] {
                            new Integer(APPTASK_1), app, progress});
                }

                publishProgress(new Object[] {
                        new Integer(APPTASK_1), app, progress});
                // Return result of the task
                payload.result = new Integer(42);
                break;

            case APPTASK_2:
                break;
            }
        } catch(AppException ape) {
            payload.exception = ape;
            payload.result = null;
        }

        return payload;
    }

    public static class Payload
    {
        public int taskType;
        public Object[] data;
        public Object result;
        public Exception exception;

        public Payload(int taskType, Object[] data) {
            this.taskType = taskType;
            this.data = data;
        }
    }
}

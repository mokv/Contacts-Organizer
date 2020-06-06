package Utils;

import android.content.Context;
import android.widget.Toast;

public class ToastService {

    Context context;

    public ToastService(Context context){
        this.context = context;
    }

    public void RaiseMessage(String message){
        Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
    }
}
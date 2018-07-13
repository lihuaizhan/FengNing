package com.example.tps900.tps900.WEBSERVICE_Utils;

import android.util.Log;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import static android.content.ContentValues.TAG;

public class WebServiceUtils {
    public static String getRemoteInfo(SoapObject request, String url, String soap_Action) {
        String result = null;
        try {
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.bodyOut = request;
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);
            HttpTransportSE ht = new HttpTransportSE(url);
            ht.call(soap_Action, envelope);
            Object object = envelope.getResponse();
            result = object.toString();
            System.out.println("WebService" + result);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "WebServiceUtils_getRemoteInfo: 请求失败：" + e);
        }
        return result;
    }
}

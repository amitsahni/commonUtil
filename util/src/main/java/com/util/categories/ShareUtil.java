package com.util.categories;

import android.content.Context;
import android.content.Intent;


/**
 * The type ShareUtil.
 */
public final class ShareUtil {
    private ShareUtil() {

    }

    /**
     * ShareUtil via Email
     *
     * @param context   the context
     * @param email     destination email (e.g. support@company.com)
     * @param subject   email subject
     * @param emailBody email body
     */
    public static void sendEmail(Context context, String email, String subject, String emailBody) {
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setType("message/rfc822");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{email});
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
        emailIntent.putExtra(Intent.EXTRA_TEXT, emailBody);
        emailIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        try {
            context.startActivity(emailIntent);
        } catch (android.content.ActivityNotFoundException ex) {
            LogUtil.e("There are no email clients installed.");
        }
    }


    /**
     * Generic method for sharing that Deliver some data to someone else. Who
     * the data is being delivered to is not specified; it is up to the receiver
     * of this action to ask the user where the data should be sent.
     *
     * @param context the context
     * @param subject The title, if applied
     * @param message Message to be delivered
     */
    public static void genericSharing(Context context, String subject, String message) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, message);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}

package bangkokguy.development.android.dbtest;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class MainActivity extends Activity {

    private static String TAG="dbtest";
    private ListView listView;

    static final String[] CONTACTS_SUMMARY_PROJECTION = new String[] {
            ContactsContract.Contacts._ID,
            ContactsContract.Contacts.DISPLAY_NAME,
            ContactsContract.Contacts.CONTACT_STATUS,
            ContactsContract.Contacts.CONTACT_PRESENCE,
            ContactsContract.Contacts.PHOTO_ID,
            ContactsContract.Contacts.LOOKUP_KEY,
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    /*first cursor example with sql database */
        SQLiteDatabase mydatabase;

        mydatabase = this.openOrCreateDatabase("DatabaseName", MODE_PRIVATE, null);
        Log.d(TAG, "openOrCreateDatabase");
        
        mydatabase.execSQL("CREATE TABLE IF NOT EXISTS TutorialsPoint(Username VARCHAR,Password VARCHAR);");
        Log.d(TAG, "CREATE TABLE");
        
        mydatabase.execSQL("INSERT INTO TutorialsPoint VALUES('admin','admin');");
        Log.d(TAG, "INSERT");

        Cursor resultSet = mydatabase.rawQuery("Select * from TutorialsPoint",null);
        Log.d(TAG, "select *");

        resultSet.moveToFirst();
        Log.d(TAG, "moveToFirst");

    /*second cursor example with contacts provider : code here overrides data from first example */
        resultSet = getContacts();
        resultSet.moveToFirst();
        Log.d(TAG, "moveToFirst");

        //String username = resultSet.getString(0);
        //Log.d(TAG, username);
        //String password = resultSet.getString(1);
        //Log.d(TAG, password);

        // These are the Contacts rows that we will retrieve.
        ListAdapter adapter = new SimpleCursorAdapter(this, // Context.
                R.layout.listview_row, // Specify the row template
                // to use (here, two
                // columns bound to the
                // two retrieved cursor
                // rows).
                resultSet, // Pass in the cursor to bind to.
                // Array of cursor columns to bind to.
                //new String[] { ContactsContract.Contacts._ID,
                //        ContactsContract.Contacts.DISPLAY_NAME_PRIMARY/*CUSTOM_RINGTONE/DISPLAY_NAME*/ },
                CONTACTS_SUMMARY_PROJECTION,
                // Parallel array of which template objects to bind to those
                // columns.
                new int[] { R.id.FirstText, R.id.SecondText, R.id.ThirdText});

        // Bind to our new adapter.

        TextView helloWorld = (TextView)findViewById(R.id.textView);


        Faszom faszom = new Faszom();
        helloWorld.setOnLongClickListener(faszom);

        listView = (ListView)findViewById(R.id.myListView);
        listView.setAdapter(adapter);
    }

    private class Faszom implements View.OnLongClickListener
    {

        @Override
        public boolean onLongClick(View v) {
            TextView tv = (TextView) v;
            tv.setText("bazd meg");
            v=tv;
            return true;
        }
    };

    private Cursor getContacts() {
        // Run query
        Uri uri = ContactsContract.Contacts.CONTENT_URI;
        String[] projection = CONTACTS_SUMMARY_PROJECTION; //new String[] { ContactsContract.Contacts._ID,
                //ContactsContract.Contacts.DISPLAY_NAME };
        String selection = ContactsContract.Contacts.IN_VISIBLE_GROUP + " = '"
                + ("1") + "'";
        String[] selectionArgs = null;
        String sortOrder = ContactsContract.Contacts.DISPLAY_NAME
                + " COLLATE LOCALIZED ASC";

        return managedQuery(uri, projection, selection, selectionArgs,
                sortOrder);
    }

}

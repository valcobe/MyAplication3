package com.example.surtidointentimpl;


import android.Manifest;
import android.app.Activity;
import android.app.SearchManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;


public class MainActivity extends Activity implements OnClickListener{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.main);

		Button btn1 = findViewById(R.id.button1);
		Button btn2 = findViewById(R.id.button2);
		Button btn3 = findViewById(R.id.button3);
		Button btn4 = findViewById(R.id.button4);
		Button btn5 = findViewById(R.id.button5);
		Button btn6 = findViewById(R.id.button6);
		Button btn7 = findViewById(R.id.button7);
		Button btn8 = findViewById(R.id.button8);
		Button btn9 = findViewById(R.id.button9);
		Button btn10 = findViewById(R.id.button10);

		btn1.setOnClickListener(this);
		btn2.setOnClickListener(this);
		btn3.setOnClickListener(this);
		btn4.setOnClickListener(this);
		btn5.setOnClickListener(this);
		btn6.setOnClickListener(this);
		btn7.setOnClickListener(this);
		btn8.setOnClickListener(this);
		btn9.setOnClickListener(this);
		btn10.setOnClickListener(this);


		if (Build.VERSION.SDK_INT >= 23)
			if (! ckeckPermissions())
				requestPermissions();
	}

	public void onClick (View v) {
		Intent in;
		final String lat = getString(R.string.lat);
		final String lon = getString(R.string.lon);
		final String url = getString(R.string.url);
		final String adressa = getString(R.string.direccion);
		final String textoABuscar = getString(R.string.textoABuscar);

		switch (v.getId()) {

			case R.id.button1:
				Toast.makeText(this, getString(R.string.opcion1), Toast.LENGTH_LONG).show();
				in = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:" + lat + ',' + lon));
				startActivity(in);
				break;

			case R.id.button2:
				Toast.makeText(this, getString(R.string.opcion2), Toast.LENGTH_LONG).show();
				in = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0?q=" + adressa));
				startActivity(in);
				break;

			case R.id.button3:
				Toast.makeText(this, getString(R.string.opcion3), Toast.LENGTH_LONG).show();
				in = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
				startActivity(in);
				break;

			case R.id.button4:
				Toast.makeText(this, getString(R.string.opcion4), Toast.LENGTH_LONG).show();
				in = new Intent(Intent.ACTION_WEB_SEARCH);
				in.putExtra(SearchManager.QUERY, textoABuscar);
				startActivity(in);
				break;

			case R.id.button5:
				Toast.makeText(this, getString(R.string.opcion5), Toast.LENGTH_LONG).show();
				in = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + getText(R.string.telef)));
				startActivity(in);
				break;

			case R.id.button6:
				Toast.makeText(this, getString(R.string.opcion6), Toast.LENGTH_LONG).show();
				in = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + getText(R.string.telef)));
				startActivity(in);
				break;

			case R.id.button7:
				Toast.makeText(this, getString(R.string.opcion7), Toast.LENGTH_LONG).show();
				//in = new Intent(Intent.ACTION_VIEW, ContactsContract.Contacts.CONTENT_URI);
				//startActivity(in);
				in = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
				startActivityForResult(in, 0);
				break;

			case R.id.button8:
				Toast.makeText(this, getString(R.string.opcion8), Toast.LENGTH_LONG).show();
				in = new Intent(Intent.ACTION_SENDTO);
				in.setData(Uri.parse("sms:" + getText(R.string.smsDest2)));
				in.putExtra(Intent.EXTRA_TEXT, getText(R.string.textMiss));
				startActivity(in);
				break;

			case R.id.button9:
				Toast.makeText(this, getString(R.string.opcion9), Toast.LENGTH_LONG).show();
				in = new Intent(Intent.ACTION_SENDTO);
				in.setData(Uri.parse("mailto:" + getText(R.string.mail)));
				in.putExtra(Intent.EXTRA_TEXT, getText(R.string.textMiss));
				in.putExtra(Intent.EXTRA_SUBJECT, getText(R.string.demo));
				startActivity(in);
				break;
			
			case R.id.button10:
				Toast.makeText(this, getString(R.string.opcion10), Toast.LENGTH_LONG).show();
				//in = new Intent(Intent.ACTION_VIEW, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
				//startActivity(in);
				in = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
				startActivityForResult(in, 1);
				break;


		}
	}

	protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
		if (requestCode == 0) {
			if (resultCode == RESULT_OK) {
				TextView tw = findViewById(R.id.textContact);
				Uri uri = data.getData();
				Cursor cu = getContentResolver().query(uri, null, null, null, null);
				cu.moveToFirst();
				int nameIndex = cu.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
				String name = cu.getString(nameIndex);
				tw.setText(name);
			}
		}else if(requestCode == 1) {
			if (resultCode == RESULT_OK) {
				ImageView img = findViewById(R.id.imageView);
				img.setImageURI(data.getData());
			}
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (Build.VERSION.SDK_INT >= 23)
			if (! ckeckPermissions())
				requestPermissions();
	}

	private boolean ckeckPermissions() {
		if (Build.VERSION.SDK_INT >= 23) {
			return ckeckPermissionsCallPhone() && ckeckPermissionsReadContacts();
		}
		else
			return true;
	}

	private boolean ckeckPermissionsCallPhone() {
		return ActivityCompat.checkSelfPermission(getApplicationContext(),
				Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED;
	}

	private boolean ckeckPermissionsReadContacts() {
		return ActivityCompat.checkSelfPermission(getApplicationContext(),
				Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED;
	}

	private void requestPermissions() {
		ActivityCompat.requestPermissions(MainActivity.this,
				new String[]{Manifest.permission.READ_CONTACTS, Manifest.permission.CALL_PHONE}, 0);
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}

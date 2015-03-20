package ro.pub.cs.systems.pdsd.lab04.contactsmanager;

import java.util.ArrayList;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class Fragment1 extends Fragment {
	
	final public static int CONTACTS_MANAGER_NATIVE_REQUEST_CODE =2;
	
	EventListener el = new EventListener();

	@Override
	public View onCreateView ( LayoutInflater li, ViewGroup vg, Bundle b )
	{
		return li.inflate( R.layout.frame1, vg, false ) ;
	}
	
	@Override
	public void onActivityCreated(Bundle state)
	{
		super.onActivityCreated( state ) ;
		/* Hide / Show */
		Button hideShow = (Button) getActivity().findViewById(R.id.button1 ) ;
		hideShow.setOnClickListener(el);
		/* Save */
		Button save = (Button) getActivity().findViewById(R.id.button2 ) ;
		save.setOnClickListener(el);
		/* Cancel */
		Button cancel = (Button) getActivity().findViewById(R.id.button3 ) ;
		cancel.setOnClickListener(el);
		
		EditText phoneNumberET = (EditText)getActivity().findViewById(R.id.editText2);
		phoneNumberET.setText(getActivity().getIntent().getStringExtra("ro.pub.cs.systems.pdsd.lab04.contactsmanager.PHONE_NUMBER_KEY"));
	}
	private class EventListener implements View.OnClickListener
	{
		@Override
		public void onClick(View v) {
			if ( v instanceof Button )
			{
				/* Hide / Show */
				if ( v.getId() == R.id.button1 )
				{
					FragmentManager fragmentManager = getActivity().getFragmentManager();
					FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
					Fragment2 fragment2 = (Fragment2)fragmentManager.findFragmentById(R.id.frame2);
					if (fragment2 == null) {
					  fragmentTransaction.add(R.id.frame2, new Fragment2());
					  ((Button)v).setText(getActivity().getResources().getString(R.string.button1hide));
					  fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_ENTER_MASK);
					} else {
					  fragmentTransaction.remove(fragment2);
					  ((Button)v).setText(getActivity().getResources().getString(R.string.button1show));
					  fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_EXIT_MASK);
					}
					fragmentTransaction.commit();
				}
				/* Save */
				if ( v.getId() == R.id.button2 )
				{
					EditText et_name = (EditText)getActivity().findViewById(R.id.editText1);
					String name = et_name.getText().toString() ;
					EditText et_phone = (EditText)getActivity().findViewById(R.id.editText2);
					String phone = et_phone.getText().toString() ;
					EditText et_email = (EditText)getActivity().findViewById(R.id.editText2);
					String email = et_email.getText().toString() ;
					EditText et_address = (EditText)getActivity().findViewById(R.id.editText2);
					String address = et_address.getText().toString() ;
					EditText et_jobTitle = (EditText)getActivity().findViewById(R.id.editText2);
					String jobTitle = et_jobTitle.getText().toString() ;
					EditText et_company = (EditText)getActivity().findViewById(R.id.editText2);
					String company = et_company.getText().toString() ;
					EditText et_website = (EditText)getActivity().findViewById(R.id.editText2);
					String website = et_website.getText().toString() ;
					EditText et_im = (EditText)getActivity().findViewById(R.id.editText2);
					String im = et_im.getText().toString() ;
					
					Intent intent = new Intent(ContactsContract.Intents.Insert.ACTION);
					intent.setType(ContactsContract.RawContacts.CONTENT_TYPE);
					if (name != null) {
					  intent.putExtra(ContactsContract.Intents.Insert.NAME, name);
					}
					if (phone != null) {
					  intent.putExtra(ContactsContract.Intents.Insert.PHONE, phone);
					}
					if (email != null) {
					  intent.putExtra(ContactsContract.Intents.Insert.EMAIL, email);
					}
					if (address != null) {
					  intent.putExtra(ContactsContract.Intents.Insert.POSTAL, address);
					}
					if (jobTitle != null) {
					  intent.putExtra(ContactsContract.Intents.Insert.JOB_TITLE, jobTitle);
					}
					if (company != null) {
					  intent.putExtra(ContactsContract.Intents.Insert.COMPANY, company);
					}
					ArrayList<ContentValues> contactData = new ArrayList<ContentValues>();
					if (website != null) {
					  ContentValues websiteRow = new ContentValues();
					  websiteRow.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Website.CONTENT_ITEM_TYPE);
					  websiteRow.put(ContactsContract.CommonDataKinds.Website.URL, website);
					  contactData.add(websiteRow);
					}
					if (im != null) {
					  ContentValues imRow = new ContentValues();
					  imRow.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Im.CONTENT_ITEM_TYPE);
					  imRow.put(ContactsContract.CommonDataKinds.Im.DATA, im);
					  contactData.add(imRow);
					}
					intent.putParcelableArrayListExtra(ContactsContract.Intents.Insert.DATA, contactData);
					getActivity().startActivityForResult(intent, CONTACTS_MANAGER_NATIVE_REQUEST_CODE);
				}
				/* Cancel */
				if ( v.getId() == R.id.button3 )
				{
					getActivity().setResult(Activity.RESULT_CANCELED, new Intent());
					getActivity().finish();
				}
			}
		}
	}
}
package com.herma.apps.drivertraining;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.view.View;

import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.Menu;
import android.widget.Toast;

import java.util.ArrayList;

import com.herma.apps.drivertraining.about.About_us;
import com.herma.apps.drivertraining.questions.adaptersj.ViewPagerAdapter;
import com.herma.apps.drivertraining.questions.fragments.QuestionsFragment;
import com.herma.apps.drivertraining.questions.fragments.SettingsActivity;



public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    QuestionsFragment questionsFragment;
    public ViewPager fragmentViewPager;
    final ArrayList<Fragment> fragmentArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

//0
        questionsFragment = new QuestionsFragment();
        fragmentArrayList.add(questionsFragment);


        fragmentViewPager = findViewById(R.id.pager);
        fragmentViewPager.setOffscreenPageLimit(1);
        ViewPagerAdapter mPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), fragmentArrayList);
        fragmentViewPager.setAdapter(mPagerAdapter);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        int QUESTIONNAIRE_REQUEST = 2018;


//        if (requestCode == QUESTIONNAIRE_REQUEST)
//        {
        if (resultCode == RESULT_OK) {


            questionsFragment.queId = data.getStringArrayExtra("queId");
            questionsFragment.timer = data.getStringExtra("timer");
            questionsFragment.current_questions = data.getStringArrayExtra("questions");
            questionsFragment.answerKey = data.getStringArrayExtra("answerKey");
            questionsFragment.response = data.getStringArrayExtra("response");

            questionsFragment.packege = data.getStringExtra("packege");

            questionsFragment.examResult();

//                questionsFragment = new QuestionsFragment();
//                questionsFragment.setArguments(bundle);
//
//                fragmentArrayList.set(0, questionsFragment);
//
//                ViewPagerAdapter queAdapter = new ViewPagerAdapter(getSupportFragmentManager(), fragmentArrayList);
//
//                fragmentViewPager.setOffscreenPageLimit(1);
//                fragmentViewPager.setAdapter(queAdapter);

            fragmentViewPager.setCurrentItem(0);
        }
//        }

//    if (requestCode == QUESTIONNAIRE_REQUEST)
//        {
//            if (resultCode == RESULT_OK)
//            {
//
//                current_questions = data.getStringArrayExtra("questions");
//                answerKey = data.getStringArrayExtra("answerKey");
//                response = data.getStringArrayExtra("response");
//
//                int score = 0;
//
//                if (answerKey.length > 0)
//                {
//                    for (int i = 0; i < answerKey.length; i++) {
//                        if(answerKey[i].equals("***"+response[i]))
//                            score++;
//                    }
//                }
//                txtScore.setText("Score : " + score+"/"+answerKey.length);
//                resultButton.setVisibility(View.VISIBLE);
//                txtScore.setVisibility(View.VISIBLE);
//                Toast.makeText(this, "Completed!!", Toast.LENGTH_LONG).show();
//            }
//        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case R.id.action_settings:
                startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
                return true;
            case R.id.action_rate:
                Toast.makeText(MainActivity.this, "Rate this app :)", Toast.LENGTH_SHORT).show();
                rateApp();
                return true;
            case R.id.action_store:
                Toast.makeText(MainActivity.this, "More apps by us :)", Toast.LENGTH_SHORT).show();
                openUrl("https://play.google.com/store/apps/developer?id=Herma%20plc");
                return true;
            case R.id.action_about:
                startActivity(new Intent(getApplicationContext(), About_us.class));
                return true;
            case R.id.action_exit:

                System.exit(0);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_exam_questions) {
            fragmentViewPager.setCurrentItem(0);
            MainActivity.this.setTitle(R.string.exam_questions);

        } else if (id == R.id.nav_share) {
            Intent sendIntent = new Intent();
            sendIntent.setAction("android.intent.action.SEND");
            sendIntent.putExtra("android.intent.extra.SUBJECT", MainActivity.this.getText(R.string.app_name));
            sendIntent.putExtra("android.intent.extra.TEXT", "Downloads \nhttps://play.google.com/store/apps/details?id=" + MainActivity.this.getPackageName());
            sendIntent.setType("text/plain");
            MainActivity.this.startActivity(Intent.createChooser(sendIntent, MainActivity.this.getText(R.string.app_name)));

        } else if (id == R.id.nav_rate) {
            Toast.makeText(MainActivity.this, "Rate this app :)", Toast.LENGTH_SHORT).show();
            rateApp();
            return true;
        } else if (id == R.id.nav_store) {
            Toast.makeText(MainActivity.this, "More apps by us :)", Toast.LENGTH_SHORT).show();
            openUrl("https://play.google.com/store/apps/developer?id=Herma%20plc");
            return true;
        } else if (id == R.id.nav_about) {
            startActivity(new Intent(getApplicationContext(), About_us.class));
            return true;
        } else if (id == R.id.nav_exit) {
        System.exit(0);
            return true;
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public void rateApp() {
        try {
            Intent rateIntent = rateIntentForUrl("market://details");
            startActivity(rateIntent);
        } catch (ActivityNotFoundException e) {
            Intent rateIntent = rateIntentForUrl("https://play.google.com/store/apps/details");
            startActivity(rateIntent);
        }
    }

    private Intent rateIntentForUrl(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(String.format("%s?id=%s", url, getPackageName())));
        int flags = Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_MULTIPLE_TASK;
        if (Build.VERSION.SDK_INT >= 21) {
            flags |= Intent.FLAG_ACTIVITY_NEW_DOCUMENT;
        } else {
            //noinspection deprecation
            flags |= Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET;
        }
        intent.addFlags(flags);
        return intent;
    }

    private void openUrl(String url) {
        Uri uri = Uri.parse(url); // missing 'http://' will cause crashed
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

}

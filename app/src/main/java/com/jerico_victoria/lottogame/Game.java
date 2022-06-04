package com.jerico_victoria.lottogame;

import static java.lang.Integer.parseInt;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Button;
import android.os.Bundle;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import android.widget.LinearLayout;
import android.widget.Spinner;


public class Game extends AppCompatActivity {
    String value, listString, finalBets;
    TextView name, balance, bets_view, list, winning_numbers, total_bet, total_win;
    Button add_bets, draw, reset, bet_again;
    LinearLayout bet_settings, menu;
    Spinner multiplier_list;
    int money = 1000, temp, totalBet = 0, totalWin = 0;
    private String[] id;
    private TextView[] btn = new TextView[45];
    private int GlobalIndex;
    ArrayList<ArrayList<Integer>> betList = new ArrayList<ArrayList<Integer>>();
    ArrayList<Integer> tempList = new ArrayList<Integer>();
    ArrayList<String> multipliers = new ArrayList<String>();
    ArrayList<Integer> WinningNumbers = new ArrayList<Integer>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        name = (TextView)findViewById(R.id.name);
        balance = (TextView)findViewById(R.id.user_money);
        bets_view = (TextView)findViewById(R.id.user_bets);
        winning_numbers = (TextView)findViewById(R.id.winning_numbers);
        list = (TextView)findViewById(R.id.list);
        total_bet = (TextView)findViewById(R.id.total_bet);
        total_win = (TextView)findViewById(R.id.total_win);
        menu = (LinearLayout)findViewById(R.id.menu);
        add_bets = (Button)findViewById(R.id.add_bets);
        draw = (Button)findViewById(R.id.draw_button);
        reset = (Button)findViewById(R.id.reset);
        bet_again = (Button)findViewById(R.id.bet_again);
        multiplier_list = findViewById(R.id.spinner_multiplier);
        bet_settings = findViewById(R.id.bet_settings);
        GlobalIndex = 0;
        final Toast[] toast = new Toast[1];
        //Bind ID's to TextView
        id = new String[]{"btn1", "btn2", "btn3", "btn4", "btn5", "btn6", "btn7", "btn8", "btn9", "btn10", "btn11", "btn12", "btn13", "btn14", "btn15", "btn16", "btn17","btn18", "btn19", "btn20", "btn21", "btn22", "btn23", "btn24", "btn25", "btn26", "btn27", "btn28", "btn29", "btn30", "btn31", "btn32", "btn33", "btn34", "btn35", "btn36", "btn37", "btn38", "btn39", "btn40", "btn41", "btn42", "btn43", "btn44", "btn45"};
        for(int i=0; i < id.length; i++){
            temp = getResources().getIdentifier(id[i], "id", getPackageName());
            btn[i] = (TextView)findViewById(temp); //Binabind na yung textviews sa part na to.
        }

        //Display numbers to textview
        for(int i = 0; i < id.length; i++){
            Log.d("myTag", "Btn length: " + id[i]);
            btn[i].setText(String.valueOf(i + 1));
            int finalI = i;
            btn[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    toggle(finalI);
                }
            });
        }

        //Get The Passed Data (Username) from previous activity
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            value = extras.getString("user"); //Set
        } else {
            value = "Player";
        }
        name.setText("Player: " + value);
        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(this, R.array.game_multiplier, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        multiplier_list.setAdapter(adapter);

        draw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                generateWinningNumbers();
                for(int i = 0; i < btn.length; i++){
                    btn[i].setEnabled(false);
                }
                int initialMoney = money;
                for (int i = 0; i < betList.size(); i++){
                    int counter = 0;
                    ArrayList<Integer> tempContainer = new ArrayList<Integer>(betList.get(i));
                    tempContainer.removeAll(WinningNumbers);
                    Log.d("Cleansed array: ", tempContainer.toString());
                    counter = tempContainer.size();
                    switch (counter){
                        case 3:
                            Log.d("Correct", String.valueOf(counter));
                            money += 100 * parseInt(multipliers.get(i));
                            totalWin += 100 * parseInt(multipliers.get(i));
                            break;
                        case 2:
                            Log.d("Correct", String.valueOf(counter));
                            money += 1500 * parseInt(multipliers.get(i));
                            totalWin += 1500 * parseInt(multipliers.get(i));
                            break;
                        case 1:
                            Log.d("Correct", String.valueOf(counter));
                            money += 50000 * parseInt(multipliers.get(i));
                            totalWin += 50000 * parseInt(multipliers.get(i));
                            break;
                        case 0:
                            Log.d("Correct", String.valueOf(counter));
                            money += 9000000 * parseInt(multipliers.get(i));
                            totalWin += 9000000 * parseInt(multipliers.get(i));
                            break;
                    }
                    balanceUpdate();
                    setTotal_win();
                }
                winning_numbers.setVisibility(View.VISIBLE);
                menu.setVisibility(View.VISIBLE);
                draw.setVisibility(View.GONE);
                bets_view.setText("Bets: ");
            }
        });

        add_bets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                draw.setVisibility(View.VISIBLE);
                if (GlobalIndex == 100){
                    toast[0] = Toast.makeText(getApplicationContext(),"Max number of Bets Reached",Toast.LENGTH_SHORT);
                    toast[0].show();
                } else {
                    String dropdown_value = multiplier_list.getSelectedItem().toString();
                    int bet_value = 10;
                    switch (dropdown_value){
                        case "x2":
                            bet_value *= 2;
                            break;
                        case "x4":
                            bet_value *= 4;
                            break;
                        case "x6":
                            bet_value *= 6;
                            break;
                        case "x8":
                            bet_value *= 8;
                            break;
                        case "x10":
                            bet_value *= 10;
                            break;
                        case "x12":
                            bet_value *= 12;
                            break;
                        case "x14":
                            bet_value *= 14;
                            break;
                        case "x16":
                            bet_value *= 16;
                            break;
                        case "x18":
                            bet_value *= 18;
                            break;
                        case "x20":
                            bet_value *= 20;
                            break;
                    }
                    multipliers.add(String.valueOf(bet_value));
                    if (money < bet_value){
                        toast[0] = Toast.makeText(getApplicationContext(),"Insufficient Balance",Toast.LENGTH_SHORT);
                        toast[0].show();
                    } else {
                        bets_view.setText("Bets: ");
                        totalBet += bet_value;
                        money -= bet_value;
                        balanceUpdate();
                        setTotal_bet();
                        finalBets = TextUtils.join(", ", tempList);
                        betList.add(new ArrayList<Integer>(tempList));
                        String previewList = list.getText().toString();
                        previewList += "\nBet #" + (GlobalIndex + 1) + ": "+ betList.get(GlobalIndex).toString();
                        list.setText(previewList);
                        GlobalIndex++;
                        for(int i = 0; i < btn.length; i++){
                            btn[i].setEnabled(true);
                            btn[i].setBackgroundColor(Color.GRAY);
                        }
                        listString = "";
                        tempList.clear();
                        bet_settings.setVisibility(View.GONE);
                    }
                }
            }
        });

        bet_again.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetui();
            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetui();
                totalBet = totalWin = 0;
                money = 1000;
                setTotal_bet();
                setTotal_win();
                balanceUpdate();
            }
        });
    }

    private void resetui(){
        draw.setVisibility(View.GONE);
        winning_numbers.setVisibility(View.GONE);
        menu.setVisibility(View.GONE);
        bets_view.setText("Bets: ");
        list.setText("List");
        for(int i = 0; i < btn.length; i++){
            btn[i].setEnabled(true);
            btn[i].setBackgroundColor(Color.GRAY);
        }
        betList.clear();
        tempList.clear();
        multipliers.clear();
        WinningNumbers.clear();
        GlobalIndex = 0;
    }

    private void generateWinningNumbers(){
        Integer[] arr = new Integer[45];
        for (int i = 0; i < 45; i++) {
            arr[i] = i + 1;
        }
        //Collections.shuffle(Arrays.asList(arr));
        for (int i = 0; i < 6; i++){
            WinningNumbers.add(arr[i]);
        }
        winning_numbers.setText("Winning Numbers\n" + TextUtils.join(" - ", WinningNumbers));
    }

    private void balanceUpdate(){
        balance.setText("Balance: " + money);
    }

    private void setTotal_bet(){
        total_bet.setText("Total Bet: " + totalBet);
    }

    private void setTotal_win(){
        total_win.setText("Total Win: " + totalWin);
    }

    private void toggle(final int index){
        btn[index].setBackgroundColor(Color.RED);
        int val = parseInt(btn[index].getText().toString());
        Log.d("Index: ", String.valueOf(index));
        Log.d("Val: ", String.valueOf(val));

        if(tempList.contains(val)){
            int numIndex = tempList.indexOf(val);
            tempList.remove(numIndex);
            btn[index].setBackgroundColor(Color.GRAY);
        } else {
            tempList.add(val);
            btn[index].setBackgroundColor(Color.RED);
        }
        if (tempList.size() >= 6) {
            bet_settings.setVisibility(View.VISIBLE);
            for(int i = 0; i < 45; i++){
                btn[i].setEnabled(false);
            }

            for(int i = 0; i < tempList.size(); i++){
                int num = parseInt(tempList.get(i).toString());
                Log.d("Num","Num is equal to btn [" + num + "]");
                btn[num - 1].setEnabled(true);
            }
        } else {
            bet_settings.setVisibility(View.GONE);
            for(int i = 0; i < 45; i++){
                btn[i].setEnabled(true);
            }
        }
        Log.d("Templistt: ", tempList.toString());
        listString = TextUtils.join(" - ", tempList);
        bets_view.setText("Bets: " + listString);
    }
}
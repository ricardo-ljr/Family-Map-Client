package Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;

import com.example.family_map_client.BuildConfig;
import com.example.family_map_client.DataCache;
import com.example.family_map_client.MainActivity;
import com.example.family_map_client.R;

public class SettingsActivity extends AppCompatActivity {

    Context context = this;
    DataCache data = DataCache.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        // Family Map Switch Settings:

        Switch lifeStoryLinesSwitch = (Switch) findViewById(R.id.lifeStoryLinesSwitch);

        if (data.isLifeStoryLinesOn()) {
            lifeStoryLinesSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked) {
                        data.setLifeStoryLinesOn(true);
                    } else {
                        data.setLifeStoryLinesOn(false);
                    }
                }
            });
        }

        Switch familyTreeLinesSwitch = (Switch) findViewById(R.id.familyTreeLinesSwitch);

        if (data.isFamilyTreeLinesOn()) {
            familyTreeLinesSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked) {
                        data.setFamilyTreeLinesOn(true);
                    } else {
                        data.setFamilyTreeLinesOn(false);
                    }
                }
            });
        }


        Switch spouseLinesSwitch = (Switch) findViewById(R.id.spouseLinesSwitch);

        if (data.isSpouseLinesOn()) {
            spouseLinesSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked) {
                        data.setSpouseLinesOn(true);
                    } else {
                        data.setSpouseLinesOn(false);
                    }
                }
            });
        }

        Switch fatherSideSwitch = (Switch) findViewById(R.id.settingFatherSideSwitch);

        if (data.isFatherSideOn()) {
            fatherSideSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked) {
                        data.setFatherSideOn(true);
                    } else {
                        data.setFatherSideOn(false);
                    }
                }
            });
        }

        Switch motherSideSwitch = (Switch) findViewById(R.id.settingMotherSideSwitch);

        if (data.isMotherSideOn()) {
            motherSideSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked) {
                        data.setMotherSideOn(true);
                    } else {
                        data.setMotherSideOn(false);
                    }
                }
            });
        }

        Switch maleEventSwitch = (Switch) findViewById(R.id.settingMaleEventsSwitch);

        if (data.isMaleEventsOn()) {
            maleEventSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked) {
                        data.setMaleEventsOn(true);
                    } else {
                        data.setMaleEventsOn(false);
                    }
                }
            });
        }

        Switch femaleEventSwitch = (Switch) findViewById(R.id.settingFemaleEventSwitch);

        if (data.isFemaleEventsOn()) {
            femaleEventSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked) {
                        data.setFemaleEventsOn(true);
                    } else {
                        data.setFemaleEventsOn(false);
                    }
                }
            });
        }

        RelativeLayout logoutLayout = (RelativeLayout) findViewById(R.id.logoutButton);
        logoutLayout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                DataCache data = DataCache.getInstance();
                data.logout();
                Intent intent = new Intent(context, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });
    }
}

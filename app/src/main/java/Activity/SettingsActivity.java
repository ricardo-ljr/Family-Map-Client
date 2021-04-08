package Activity;

import android.content.Context;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;

import com.example.family_map_client.BuildConfig;
import com.example.family_map_client.DataCache;
import com.example.family_map_client.R;

public class SettingsActivity extends AppCompatActivity {

    Context context = this;
    DataCache data = DataCache.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // Family Map Switch Settings:

        Switch lifeStoryLinesSwitch = this.findViewById(R.id.lifeStoryLinesSwitch);

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



    }
}

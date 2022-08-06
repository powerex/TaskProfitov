package ua.com.azbest.apptest3.fragment;

import android.os.Bundle;
import android.util.Log;

import androidx.fragment.app.Fragment;

import lombok.extern.slf4j.Slf4j;
import ua.com.azbest.apptest3.model.Content;
import ua.com.azbest.apptest3.model.TextContentFactory;
import ua.com.azbest.apptest3.model.WebContentFactory;

@Slf4j
public class FragmentFactory {

    public static Fragment getFragment(Content content) {
        Fragment fragment = null;
        String data = "";
        switch (content.getType()) {
            case TEXT:
                data = TextContentFactory.getContent(content).getText();
                fragment = new TextFragment();
                break;
            case WEBPAGE:
                data = WebContentFactory.getContent(content).getUrl().toString();
                fragment = new WebFragment();
                break;
            default:
                fragment = new EmptyFragment();
                Log.d("INFO", "getFragment: Empty");
        }
        Bundle bundle = new Bundle();
        bundle.putString("data", data);
        fragment.setArguments(bundle);
        return fragment;
    }

}

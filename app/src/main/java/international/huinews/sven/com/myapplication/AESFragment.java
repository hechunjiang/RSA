package international.huinews.sven.com.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import international.huinews.sven.com.myapplication.utils.aes;


public class AESFragment extends Fragment {
    private static final String ARG_SECTION_NUMBER = "section_number";
    private EditText mingEdit;
    private EditText miyaoEdit;
    private EditText miEdit;
    private EditText ming2Edit;
    private Button initButton;
    private Button jiamiButton;
    private Button jiemiButton;
    private boolean flag = false;

    public AESFragment() {
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivity) activity).onSectionAttached(getArguments().getInt(ARG_SECTION_NUMBER));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_des, container, false);
        mingEdit = rootview.findViewById(R.id.mingEdit);
        miyaoEdit = rootview.findViewById(R.id.miyaoEdit);
        miEdit = rootview.findViewById(R.id.miEdit);
        ming2Edit = rootview.findViewById(R.id.ming2Edit);
        initButton = rootview.findViewById(R.id.initButton);
        initButton.setOnClickListener(new myClickListener());
        jiamiButton = rootview.findViewById(R.id.encryptButton);
        jiamiButton.setOnClickListener(new myClickListener());
        jiemiButton = rootview.findViewById(R.id.decryptButton);
        jiemiButton.setOnClickListener(new myClickListener());
        return rootview;
    }

    class myClickListener implements View.OnClickListener {


        @Override
        public void onClick(View view) {
            if (view == initButton) {
                //初始化密钥
                String aesKey = "";
                try {
                    aesKey = aes.initKey();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                miyaoEdit.setText(aesKey);
            }
            if (view == jiamiButton) {
                String aesKey;
                aesKey = miyaoEdit.getText().toString();
                if (aesKey.equals("")) {
                    Toast.makeText(getActivity(), "请先初始化密钥", Toast.LENGTH_SHORT).show();
                    return;
                }

                String inputStr;
                inputStr = mingEdit.getText().toString();
                if (inputStr.equals("")) {
                    Toast.makeText(getActivity(), "待加密数据不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                byte[] inputData = inputStr.getBytes();
                byte[] outputData;
                String outputStr = "";
                try {
                    outputData = aes.encrypt(inputData, aesKey);
                    outputStr = aes.encryptBASE64(outputData);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                miEdit.setText(outputStr);
            }
            if (view == jiemiButton) {
                String aesKey;
                aesKey = miyaoEdit.getText().toString();
                if (aesKey.equals("")) {
                    Toast.makeText(getActivity(), "请先初始化密钥并进行加密", Toast.LENGTH_SHORT).show();
                    return;
                }
                String outputStr;
                outputStr = miEdit.getText().toString();
                if (outputStr.equals("")) {
                    Toast.makeText(getActivity(), "密文不能为空,请先加密产生密文", Toast.LENGTH_SHORT).show();
                    return;
                }
                byte[] outputData = new byte[0];
                try {
                    outputData = aes.decrypt(aes.decryptBASE64(outputStr), aesKey);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                String jiemiStr = new String(outputData);
                if (jiemiStr.equals("")) {
                    Toast.makeText(getActivity(), "密钥不匹配，解密失败", Toast.LENGTH_SHORT).show();
                    ming2Edit.setText("");
                    return;
                }
                ming2Edit.setText(new String(outputData));
            }
        }
    }

    public static AESFragment newInstance(int sectionNumber) {
        AESFragment fragment = new AESFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

}

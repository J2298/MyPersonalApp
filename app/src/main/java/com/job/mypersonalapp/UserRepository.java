package com.job.mypersonalapp;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.job.mypersonalapp.models.User;
import com.orm.SugarRecord;

import java.util.List;

public class UserRepository {

    //public static List<User> users = new ArrayList<>();
   /* static{
        users.add(new User(100, "ebenites", "tecsup", "Erick Benites"));
        users.add(new User(200, "jfarfan", "tecsup", "Jaime Farfán"));
        users.add(new User(300, "drodriguez", "tecsup", "David Rodriguez"));
    }*/

    public static List<User> list(){
        List<User> users = SugarRecord.listAll(User.class);
        return users;
    }

    public static User login(String username, String password){
        List<User> users = list();
        for (User user : users){
            if(user.getUsername().equalsIgnoreCase(username) && user.getPassword().equals(password)){
                return user;
            }
        }
        return null;
    }

    public static Boolean valdidar(String username){
        List<User> users = list();
        for (User user : users){
            if(user.getUsername().equalsIgnoreCase(username)){
                return false;
            }
        }
        return true;
    }
    
    public static User read(Long id){
        User user = SugarRecord.findById(User.class, id);
        return user;
    }

    public static void create(String fullname, String username, String password){
        User user = new User(fullname, username, password);
        valdidar(username);
        SugarRecord.save(user);
    }

    public static void update(String fullname, String username, String password, Long id){
        User user = SugarRecord.findById(User.class, id);
        user.setFullname(fullname);
        user.setUsername(username);
        user.setPassword(password);
        SugarRecord.save(user);
    }

    public static void delete(Long id){
        User user = SugarRecord.findById(User.class, id);
        SugarRecord.delete(user);
    }
    public static User getUser(String username){
        List<User> users = list();
        for (User user : users){
            if(user.getUsername().equalsIgnoreCase(username)){
                return user;
            }
        }
        return null;
    }

    public static void showDialog(final Context context, final String username, final String password){

        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.custom_dialog);
        // Custom Android Allert Dialog Title
        dialog.setTitle("Custom Dialog Example");

        Button dialogButtonCancel = (Button) dialog.findViewById(R.id.customDialogCancel);
        Button dialogButtonOk = (Button) dialog.findViewById(R.id.customDialogOk);
        // Click cancel to dismiss android custom dialog box
        dialogButtonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Ingrese sus datos nuevamente", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

        // Your android custom dialog ok action
        // Action for custom dialog ok button click
        dialogButtonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserRepository.create(username, username, password);
                Toast.makeText(context, "Usuario Registrado", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

        dialog.show();

    }

}


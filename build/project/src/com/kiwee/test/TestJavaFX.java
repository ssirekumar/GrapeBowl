package com.kiwee.test;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class TestJavaFX extends Application {


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Tree View Sample");        

        /*TreeItem<String> rootItem = new TreeItem<String> ("Inbox");
        rootItem.setExpanded(true);
        for (int i = 1; i < 5; i++) {
            rootItem.getChildren().add(createTreeItem(i));
        }  */
        TreeItem<String> rootItem = new TreeItem<String> ("Group ");
        for (int i = 1; i < 6; i++) {
            //TreeItem<String> item = new TreeItem<String> ("Message");            
            rootItem.getChildren().add(new TreeItem<String> ("Message"));
        }
        TreeView<String> tree = new TreeView<String> (); 
        tree.setRoot(rootItem);
        StackPane root = new StackPane();
        root.getChildren().add(tree);
        primaryStage.setScene(new Scene(root, 300, 250));
        primaryStage.show();
        // print children
        printChildren(rootItem);
    }

    private TreeItem<String> createTreeItem(int num) {
        TreeItem<String> rootItem = new TreeItem<String> ("Group " + num);
        for (int i = 1; i < 6; i++) {
            TreeItem<String> item = new TreeItem<String> ("Message" + i);            
            rootItem.getChildren().add(item);
        }
        return rootItem;
    }

    private void printChildren(TreeItem<String> root){
        System.out.println("Current Parent :" + root.getValue());
        for(TreeItem<String> child: root.getChildren()){
            if(child.getChildren().isEmpty()){
                System.out.println(child.getValue());
            } else {
                printChildren(child);
            }
        }
    }
}
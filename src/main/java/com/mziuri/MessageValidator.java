package com.mziuri;

public class MessageValidator {
        private static MessageValidator instance = null;
        public String message;

        private MessageValidator() {
            this.message = "default";
        }

        public void setMessage(String message) {
            this.message = message;
        }

    public static MessageValidator getInstance(){
            if(instance == null){
                instance = new MessageValidator();
            }
            return instance;
        }
        public boolean isValid(){
            if (message.toLowerCase().contains("fuck")) return false; // cussing aint allowed cuz we aint from the hood
            if (message.toLowerCase().contains("\n")) return false;
            return true;
        }

}

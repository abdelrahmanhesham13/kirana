package com.softclads.Gagron.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class UserStatusModel {



        @SerializedName("user")
        @Expose
        private UserModel user;
        @SerializedName("status")
        @Expose
        private Boolean status;

        public UserModel getUser() {
            return user;
        }

        public void setUser(UserModel user) {
            this.user = user;
        }

        public Boolean getStatus() {
            return status;
        }

        public void setStatus(Boolean status) {
            this.status = status;
        }


}

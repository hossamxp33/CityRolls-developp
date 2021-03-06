package com.codesroots.osamaomar.cityrolls.entities;

import java.util.ArrayList;
import java.util.List;

public class Products {


    private List<ProductsbycategoryBean> data;

    public List<ProductsbycategoryBean> getProductsbycategory() {
        return data;
    }

    public void setProductsbycategory(List<ProductsbycategoryBean> productsbycategory) {
        this.data = productsbycategory;
    }

    public static class ProductsbycategoryBean {

        /**
         * id : 30
         * name : zzzz
         * name_en : salt
         * productsizes : [{"id":2,"product_id":30,"size":"100","created":"2019-02-19T07:00:10+0000","modified":"2019-02-19T07:00:10+0000","current_price":"1","start_price":"1","amount":1,"total_rating":[{"productsize_id":2,"stars":5,"count":2}]}]
         * favourites : [{"product_id":30,"id":5,"user_id":4}]
         * productphotos : [{"product_id":30,"id":1,"photo":"http://shopgate.codesroots.com/library/attachment/pds.jpg"},{"product_id":30,"id":2,"photo":"http://shopgate.codesroots.com/library/attachment/pds3.jpg"}]
         */

        private int id;
        private String name;
        private String name_en;
        private float price;
        private String img;

        private List<Productsize> productsizes;
        private List<FavouritesBean> favourites;
        private List<ProductphotosBean> productphotos;
        private List<TotalRatingBean> total_rating = new ArrayList<>();

        public void setImg(String img) {
            this.img = img;
        }

        public String getImg() {
            return img;
        }
        public float getPrice() {
            return price;
        }

        public void setPrice(float price) {
            this.price = price;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getName_en() {
            return name_en;
        }

        public void setName_en(String name_en) {
            this.name_en = name_en;
        }

        public List<Productsize> getProductsizes() {
            return productsizes;
        }

        public void setProductsizes(List<Productsize> productsizes) {
            this.productsizes = productsizes;
        }

        public List<FavouritesBean> getFavourites() {
            return favourites;
        }

        public void setFavourites(List<FavouritesBean> favourites) {
            this.favourites = favourites;
        }

        public List<ProductphotosBean> getProductphotos() {
            return productphotos;
        }

        public void setProductphotos(List<ProductphotosBean> productphotos) {
            this.productphotos = productphotos;
        }


        public List<TotalRatingBean> getTotal_rating() {


            return total_rating;
        }

        public void setTotal_rating(List<TotalRatingBean> total_rating) {
            this.total_rating = total_rating;
        }

        public static class FavouritesBean {
            public FavouritesBean(int id) {
                this.id = id;
            }

            /**
             * product_id : 30
             * id : 5
             * user_id : 4
             */


            private int product_id;
            private int id;
            private int user_id;

            public int getProduct_id() {
                return product_id;
            }

            public void setProduct_id(int product_id) {
                this.product_id = product_id;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getUser_id() {
                return user_id;
            }

            public void setUser_id(int user_id) {
                this.user_id = user_id;
            }
        }


    }
}

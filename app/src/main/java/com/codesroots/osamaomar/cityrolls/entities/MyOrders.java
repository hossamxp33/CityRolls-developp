package com.codesroots.osamaomar.cityrolls.entities;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class MyOrders {


    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean implements Serializable{
        /**
         * id : 1
         * created : 2019-03-04T14:33:12+0000
         * price : 200
         * order_status : 1
         * user_id : 5
         * type : paypal
         *     val orderdetails: List<Orderdetail>,
         * orderdetails : [{"id":1,"productsize_id":2,"order_id":1,"productsize":{"id":2,"product_id":30,"product":{"id":30,"name":"zzzz","name_en":"salt","productphotos":[{"id":1,"product_id":30,"photo":"http://shopgate.codesroots.com/library/attachment/pd1.jpg"},{"id":2,"product_id":30,"photo":"http://shopgate.codesroots.com/library/attachment/pd2.jpg"}]}},"totalproducts":[]},{"id":3,"productsize_id":3,"order_id":1,"productsize":{"id":3,"product_id":31,"product":{"id":31,"name":"zzzz","name_en":"salt","productphotos":[{"id":3,"product_id":31,"photo":"http://shopgate.codesroots.com/library/attachment/pd3.jpg"},{"id":20,"product_id":31,"photo":"http://shopgate.codesroots.com/library/attachment/pd20.jpg"}]}},"totalproducts":[]}]
         */

        private int id;
        private String created;
        @SerializedName("total")
        private String price;
        private String order_status;
        private int user_id;
        private int statues;
        private String type;
        private String type_en;
        private List<OrderdetailsBean> orderdetails;

        public Paymenttype getPaymenttype() {
            return paymenttype;
        }

        public void setPaymenttype(Paymenttype paymenttype) {
            this.paymenttype = paymenttype;
        }

        private Paymenttype paymenttype;

        public int getStatues() {
            return statues;
        }

        public void setStatues(int statues) {
            this.statues = statues;
        }

        public String getType_en() {
            return type_en;
        }

        public void setType_en(String type_en) {
            this.type_en = type_en;
        }



        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getCreated() {
            return created;
        }

        public void setCreated(String created) {
            this.created = created;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getOrder_status() {
            return order_status;
        }

        public void setOrder_status(String order_status) {
            this.order_status = order_status;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public List<OrderdetailsBean> getOrderdetails() {
            return orderdetails;
        }

        public void setOrderdetails(List<OrderdetailsBean> orderdetails) {
            this.orderdetails = orderdetails;
        }

        public static class OrderdetailsBean implements Serializable {
            /**
             * id : 1
             * productsize_id : 2
             * order_id : 1
             * productsize : {"id":2,"product_id":30,"product":{"id":30,"name":"zzzz","name_en":"salt","productphotos":[{"id":1,"product_id":30,"photo":"http://shopgate.codesroots.com/library/attachment/pd1.jpg"},{"id":2,"product_id":30,"photo":"http://shopgate.codesroots.com/library/attachment/pd2.jpg"}]}}
             * totalproducts : []
             *   "total": "20.0",
             *    "productcolor_id": 0,
             */

            private int id;
            private int productsize_id;
            private int  productcolor_id;
            private int order_id;
            @SerializedName("itemamount")
            private int amount;
            @SerializedName("item")

            private Items productsize;
            private ProductColor product_color;

            @SerializedName("itemtotal")
            private String total;
            //
            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getAmount() {
                return amount;
            }

            public void setAmount(int amount) {
                this.amount = amount;
            }
            public int getProductsize_id() {
                return productsize_id;
            }

            public void setProductsize_id(int productsize_id) {
                this.productsize_id = productsize_id;
            }
            public int getProductcolor_id() {
                return productcolor_id;
            }

            public void setProductcolor_id(int productcolor_id) {
                this.productcolor_id = productcolor_id;
            }
            public int getOrder_id() {
                return order_id;
            }

            public void setOrder_id(int order_id) {
                this.order_id = order_id;
            }

            public Items getProductsize() {
                return productsize;
            }

            public void setProductsize(Items productsize) {
                this.productsize = productsize;
            }
            public ProductColor getProduct_color() {
                return product_color;
            }

            public String getTotal() {
                return total;
            }

            public void setTotal(String total) {
                this.total = total;
            }

            public static class ProductsizeBean {
                /**
                 * id : 2
                 * "size": "20 needles",
                 * product_id : 30
                 * product : {"id":30,"name":"zzzz","name_en":"salt","productphotos":[{"id":1,"product_id":30,"photo":"http://shopgate.codesroots.com/library/attachment/pd1.jpg"},{"id":2,"product_id":30,"photo":"http://shopgate.codesroots.com/library/attachment/pd2.jpg"}]}
                 */

                private int id;
                private int product_id;
                private int amount;
                private String size;
                private float start_price;
                private float current_price;
                private ProductBean product;


                public String getSize() {
                    return size;
                }

                public void setSize(String size) {
                    this.size = size;
                }

                public int getAmount() {
                    return amount;
                }

                public void setAmount(int amount) {
                    this.amount = amount;
                }

                public float getStart_price() {
                    return start_price;
                }

                public void setStart_price(float start_price) {
                    this.start_price = start_price;
                }


                public float getCurrent_price() {
                    return current_price;
                }

                public void setCurrent_price(float current_price) {
                    this.current_price = current_price;
                }
                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public int getProduct_id() {
                    return product_id;
                }

                public void setProduct_id(int product_id) {
                    this.product_id = product_id;
                }

                public ProductBean getProduct() {
                    return product;
                }

                public void setProduct(ProductBean product) {
                    this.product = product;
                }

                public static class ProductBean {
                    /**
                     * id : 30
                     * name : zzzz
                     * name_en : salt
                     * productphotos : [{"id":1,"product_id":30,"photo":"http://shopgate.codesroots.com/library/attachment/pd1.jpg"},{"id":2,"product_id":30,"photo":"http://shopgate.codesroots.com/library/attachment/pd2.jpg"}]
                     */

                    private int id;
                    private String name;
                    private String name_en;
                    private String img;
                    private List<ProductphotosBean> productphotos;
                    private List<TotalRatingBean> total_rating;

                    public void setImg(String img) {
                        this.img = img;
                    }

                    public String getImg() {
                        return img;
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

                    public List<ProductphotosBean> getProductphotos() {
                        return productphotos;
                    }

                    public List<TotalRatingBean> getTotal_rating() {
                        return total_rating;
                    }

                    public void setTotal_rating(List<TotalRatingBean> total_rating) {
                        this.total_rating = total_rating;
                    }

                    public void setProductphotos(List<ProductphotosBean> productphotos) {
                        this.productphotos = productphotos;
                    }
                    public static class TotalRatingBean {

                        public TotalRatingBean() {
                        }

                        public TotalRatingBean(float stars, int count) {
                            this.stars = stars;
                            this.count = count;
                        }

                        /**
                         * productsize_id : 2
                         * stars : 5
                         * count : 2
                         */


                        private int product_id;
                        private float stars = 0;
                        private int count=0;

                        public int getProductsize_id() {
                            return product_id;
                        }

                        public void setProductsize_id(int productsize_id) {
                            this.product_id = productsize_id;
                        }

                        public float getStars() {
                            return stars;
                        }

                        public void setStars(float stars) {
                            this.stars = stars;
                        }

                        public int getCount() {
                            return count;
                        }

                        public void setCount(int count) {
                            this.count = count;
                        }
                    }
                    public static class ProductphotosBean {
                        /**
                         * id : 1
                         * product_id : 30
                         * photo : http://shopgate.codesroots.com/library/attachment/pd1.jpg
                         */

                        private int id;
                        private int product_id;
                        private String photo;

                        public int getId() {
                            return id;
                        }

                        public void setId(int id) {
                            this.id = id;
                        }

                        public int getProduct_id() {
                            return product_id;
                        }

                        public void setProduct_id(int product_id) {
                            this.product_id = product_id;
                        }

                        public String getPhoto() {
                            return photo;
                        }

                        public void setPhoto(String photo) {
                            this.photo = photo;
                        }
                    }
                }
            }

            public static class ProductColor{
                /*
                 *          "id": 12,
                 *                         "product_id": 308,
                 *                         "color": "????????",
                 *                         "created": "2020-12-13T18:23:30+0200"
                 */
                private int id;
                private int product_id;
                private String color;
                private String created;

                public int getId() { return id; }
                public void setId(int id) { this.id = id; }


                public int getProduct_id() {
                    return product_id;
                }

                public void setProduct_id(int product_id) {
                    this.product_id = product_id;
                }

                public String getColor() {
                    return color;
                }

                public void setColor(String color) {
                    this.color = color;
                }

                public String getCreated() {
                    return created;
                }

                public void setCreated(String created) {
                    this.created = created;
                }
            }
        }
    }
}

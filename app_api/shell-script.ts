// Paste this script on function shell for debugging purpose

//var input = { uid: "kjypVYRbNIP6jqGONdDaNDzRNb02", amount: "50" };

var input = {
  amount: "1550",
  child: "DUMMY_CHILD",
  consumer: "kjypVYRbNIP6jqGONdDaNDzRNb02",
  invoice: "DUMMY_INVOICE_1",
  merchant: "bbrEquzl57RpZI5qiZhtcXVqlnG3",
  products: [
    {
      name: "Sandwich",
      number: "1",
      picture: "dummy.jpg",
      price: "150"
    },
    {
      name: "Hamburger",
      number: "1",
      picture: "dummy.jpg",
      price: "250"
    },
    {
      name: "Fried Fries",
      number: "1",
      picture: "dummy.jpg",
      price: "350"
    },
    {
      name: "Popcorn",
      number: "2",
      picture: "dummy.jpg",
      price: "400"
    }
  ],
  state: "open",
  timestamp: "DUMMY_TIMESTAMP"
};

doPayment(input);

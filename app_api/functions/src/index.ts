import * as functions from "firebase-functions";
import * as admin from "firebase-admin";

const settings = { timestampsInSnapshots: true };

admin.initializeApp();
admin.firestore().settings(settings);

const db = admin.firestore();

/**
 * Function handling payment request from client. Input contain information of
 * transaction amount and consumer id.
 *
 * Every transaction will trigger the update of consumer balance. Balance is total accumulated
 * amount of transaction. Transaction is approved is current balance + current amount is equal less
 * then the transaction limit. Otherwise, transaction is rejected.
 *
 * If transaction is approved, function return transaction outcome code as "TC". Otherwise,
 * if transaction is rejected, function return transaction outcome code as "AAC".*
 *
 * How to debug this function:
 * 1. $ cd functions
 * 2. $ npm run-script shell
 * 3. paste text copied from shell-script.ts
 *
 */
export const doPayment = functions.https.onCall((transaction, context) => {
  console.log(transaction);

  const consumerRef = db.collection("consumers").doc(transaction.consumer);
  const merchantRef = db.collection("merchants").doc(transaction.merchant);
  const globalRef = db.collection("admin").doc("global");
  const amount = transaction.amount;

  console.log("consumerRef = " + consumerRef.path);
  console.log("merchantRef = " + merchantRef.path);

  return db
    .runTransaction(t => {
      return t.getAll(consumerRef, globalRef).then(docs => {
        console.log(docs[0].data());
        console.log(docs[1].data());

        const balanceStart = docs[0].data().balance;
        const limit = docs[0].data().limit;
        const token = docs[0].data().token;
        const invoice_counter = docs[1].data().counter_invoice;

        let value = Number(docs[0].data().balance);
        let outcome = "AAC";

        // Check transaction outcome
        if (Number(amount) + Number(balanceStart) <= Number(limit)) {
          outcome = "TC";
          value += Number(amount);
        }

        // Update transaction outcome
        transaction.outcome = outcome;

        // Convert back to string format because
        // all data are implemented in String
        // for simplicity
        const balanceEnd = String(value);

        // Update user balance
        t.update(consumerRef, {
          balance: balanceEnd
        });

        // Update transaction invoice
        transaction.invoice = String(Number(invoice_counter) + 1);

        //
        // copy transaction db location of:
        //
        // - Merchant: /merchants/{merchant_id}/transactions
        t.set(
          db
            .collection("merchants")
            .doc(transaction.merchant)
            .collection("transactions")
            .doc(transaction.invoice),
          transaction
        );

        // - Consumer: /consumers/{consumer_id}/transactions
        t.set(
          db
            .collection("consumers")
            .doc(transaction.consumer)
            .collection("transactions")
            .doc(transaction.invoice),
          transaction
        );

        // - Admin: /admin/log/transactions
        t.set(
          db
            .collection("admin")
            .doc("log")
            .collection("transactions")
            .doc(transaction.invoice),
          transaction
        );

        // Update global counter
        t.set(globalRef, { counter_invoice: transaction.invoice });

        console.log("Token => " + token);
        console.log("Transaction amount => " + amount);
        console.log("Transaction limit => " + limit);
        console.log("Transaction outcome => " + outcome);
        console.log("Start balance updated => " + balanceStart);
        console.log("End balance updated => " + balanceEnd);

        // Return transaction outcome to user
        return Promise.resolve({
          trans_result: outcome,
          user_token: token
        });
      });
    })
    .then(result => {
      console.log("Transaction successfull");
      notifyUser(result.trans_result, result.user_token);
      return { trans_result: result.trans_result };
    })
    .catch(error => {
      console.log(error);
    });
});

/**
 * Send notification to client device based on the
 * transaction outcome
 *
 * @param outcome of transaction
 * @param token of device client
 */
function notifyUser(outcome, token) {
  let message = "Your transaction is rejected";
  if (outcome === "TC") {
    message = "Your transaction is approved";
  }

  console.log("@notifyUser outcome = " + outcome);
  console.log("@notifyUser token = " + token);
  console.log("@notifyUser message = " + message);

  admin
    .messaging()
    .sendToDevice(token, {
      data: {
        title: message,
        body: "Check your transaction details"
      }
    })
    .then(function(response) {
      console.log("Successfully sent message:", response);
    })
    .catch(function(error) {
      console.log("Error sending message:", error);
    });
}

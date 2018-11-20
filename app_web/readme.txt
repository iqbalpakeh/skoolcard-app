# Web Admin Readme

# First sample to follow https://serverless-stack.com/#preface

# How to deploy to firebase 
  - Go to your admin project ../app_web/admin 
  - Build react app    : $ npm run Build
  - Init firebase      : $ firebase Init (use firestore and hosting and change to "build" folder)
  - Deploy to firebase : $ firebase deploy

# Deployment trace
 ~/Projects/project-skoolcard-1.0/app_web/admin   master ↑1                                                                                                                        1 ↵  906  09:07:38
 $ firebase init

     ######## #### ########  ######## ########     ###     ######  ########
     ##        ##  ##     ## ##       ##     ##  ##   ##  ##       ##
     ######    ##  ########  ######   ########  #########  ######  ######
     ##        ##  ##    ##  ##       ##     ## ##     ##       ## ##
     ##       #### ##     ## ######## ########  ##     ##  ######  ########

You're about to initialize a Firebase project in this directory:

  /Users/mpakeh/Projects/project-skoolcard-1.0/app_web/admin

? Which Firebase CLI features do you want to setup for this folder? Press Space to select features, then Enter to confirm your choices. Firestore: Deploy rules and create indexes for Firestore, Hosting: C
onfigure and deploy Firebase Hosting sites

=== Project Setup

First, let's associate this project directory with a Firebase project.
You can create multiple project aliases by running firebase use --add,
but for now we'll just set up a default project.

? Select a default Firebase project for this directory: project-skoolcard-1-0 (project-skoolcard-1-0)
i  Using project project-skoolcard-1-0 (project-skoolcard-1-0)

=== Firestore Setup

Firestore Security Rules allow you to define how and when to allow
requests. You can keep these rules in your project directory
and publish them with firebase deploy.

? What file should be used for Firestore Rules? firestore.rules

Firestore indexes allow you to perform complex queries while
maintaining performance that scales with the size of the result
set. You can keep index definitions in your project directory
and publish them with firebase deploy.

? What file should be used for Firestore indexes? firestore.indexes.json

=== Hosting Setup

Your public directory is the folder (relative to your project directory) that
will contain Hosting assets to be uploaded with firebase deploy. If you
have a build process for your assets, use your build's output directory.

? What do you want to use as your public directory? build
? Configure as a single-page app (rewrite all urls to /index.html)? No
? File build/404.html already exists. Overwrite? No
i  Skipping write of build/404.html
? File build/index.html already exists. Overwrite? No
i  Skipping write of build/index.html

i  Writing configuration info to firebase.json...
i  Writing project information to .firebaserc...

✔  Firebase initialization complete!

 ~/Projects/project-skoolcard-1.0/app_web/admin   master ? ↑1                                                                                                                        ✔  907  09:08:49
 $ firebase deploy

=== Deploying to 'project-skoolcard-1-0'...

i  deploying firestore, hosting
i  firestore: checking firestore.rules for compilation errors...
✔  firestore: rules file firestore.rules compiled successfully
i  firestore: uploading rules firestore.rules...
i  hosting[project-skoolcard-1-0]: beginning deploy...
i  hosting[project-skoolcard-1-0]: found 36 files in build
✔  hosting[project-skoolcard-1-0]: file upload complete
✔  firestore: released rules firestore.rules to cloud.firestore
i  hosting[project-skoolcard-1-0]: finalizing version...
✔  hosting[project-skoolcard-1-0]: version finalized
i  hosting[project-skoolcard-1-0]: releasing new version...
✔  hosting[project-skoolcard-1-0]: release complete

✔  Deploy complete!

Project Console: https://console.firebase.google.com/project/project-skoolcard-1-0/overview
Hosting URL: https://project-skoolcard-1-0.firebaseapp.com

 ~/Projects/project-skoolcard-1.0/app_web/admin   master ? ↑1                                                                                                                        ✔  908  09:09:14
 $
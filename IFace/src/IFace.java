import java.util.Scanner;

public class IFace {

    private static final int SIZE_MAX = 30;

    public static void printArrayFull(String[][] users, int size)
    {
        System.out.println();
        for(int i = 0; i < size; i++)
        {
            if(users[i][0] != null)
            {
                for(int j = 0; j < 3; j++)
                {
                    switch (j)
                    {
                        case 0:
                            System.out.println("email: " + users[i][j]);
                            break;
                        case 1:
                            System.out.println("Password: " + users[i][j]);
                            break;
                        case 2:
                            System.out.println("Name: " + users[i][j]);
                            break;
                    }
                }

            }
        }
        System.out.println();
    }

    public static void printArrayUser(String[][] users, int curUser)
    {
        int i = 0;
        while(users[curUser][i] != null)
        {
            System.out.println(users[curUser][i]);
            i++;
        }
    }

    public static void printAttributesUser(String[][][] attributeUsers, int curUser)
    {
        int i = 0;
        while(attributeUsers[curUser][i][0] != null)
        {
            System.out.println(attributeUsers[curUser][i][0] + " : " + attributeUsers[curUser][i][1]);
            i++;
        }
    }

    public static int menuIFace(Scanner input)
    {
        System.out.println("              #################\n              ###   IFACE   ###\n              #################\n1 - Login\n2 - Sign Up\n0 - Exit\n");
        return input.nextInt();
    }

    public static int menuUser(Scanner input, String[][] users, int loggedinUser)
    {
        System.out.println("              #######################\n                     Welcome " + users[loggedinUser][2] + " !!\n              #######################\n" +
                "0 - Log out\n" +
                "1 - Add friend\n" +
                "2 - Check friends requests\n" +
                "3 - Messages(" + users[loggedinUser][4] + ")\n" +
                "4 - Create a community\n" +
                "5 - Enter a community\n" +
                "6 - Profile\n" +
                "7 - Edit/Add an attribute\n" +
                "8 - Delete account");
        return input.nextInt();
    }

    public static int searchUsers(String searching, String[][] users, int size, int infoIndex)
    {
        int indexFound = -9999;
        for(int i = 0; i < SIZE_MAX; i++)
        {
            if(users[i][infoIndex] != null)
            {
                if(searching.equals(users[i][infoIndex]))
                {
                    indexFound = i;
                }
            }
        }

        return indexFound;
    }

    public static int searchAttribute(String searching, String[][][] attribute, int userIndex, int size, int infoIndex)
    {
        int indexFound = -9999;
        for(int i = 0; i < SIZE_MAX; i++)
        {
            if(attribute[userIndex][i][infoIndex] != null)
            {
                if(searching.equals(attribute[userIndex][i][infoIndex]))
                {
                    indexFound = i;
                    break;
                }

            }
        }

        return indexFound;
    }

    public static void signUpUser(Scanner input, String[][] users, int size)
    {
        input.skip("\n");
        int thereIs;
        int retrying = 1;
        String email;
        do
        {

            System.out.print("Insert your email:\t");
            email = input.nextLine();
            thereIs = searchUsers(email, users, size, 0);
            if(thereIs != -9999)
            {
                System.out.println("There's already a user with that email.\nWould you like to try again?\n 1 - YES\n2 - NO");
                retrying = input.nextInt();
                input.skip("\n");
            }

        }while(thereIs != -9999 && retrying == 1);

        if(thereIs == -9999)
        {
            int i = 0;
            while(users[i][0] != null)
            {
                i++;
            }
            users[i][0] = email;
            System.out.print("Insert your password:\t");
            users[i][1] = input.nextLine();
            System.out.print("Insert your name:\t");
            users[i][2] = input.nextLine();
            users[i][3] = "0"; //friends
            users[i][4] = "0"; //messages
            System.out.println("Account created successfully!\n");
        }

    }

    public static int logInUser(Scanner input, String[][] users, int size)
    {
        input.skip("\n");
        String search;
        //Info info;
        System.out.print("Insert your email:\t");
        search = input.nextLine();
        int indexLogIn = searchUsers(search, users, size, 0); //0 -> email
        if(indexLogIn == -9999)
        {
            System.out.println("That's not a valid e-mail. Please Sign Up first.");
            return -9999;
        }
        else
        {
            boolean gotIt = false;
            while (!gotIt)
            {
                System.out.print("Insert your password:\t");
                search = input.nextLine();
                if(users[indexLogIn][1].equals(search))
                {
                    System.out.println("You`re logged in!\n");
                    return indexLogIn;
                }
                else
                {
                    System.out.println("Incorrect Password!");
                    System.out.println("Retry?\n1 - YES\n0 - NO");
                    int command = input.nextInt();
                    input.skip("\n");
                    if(command == 0) return -9999;
                }
            }
        }
        return  -9999;
    }

    public static void addFriend(Scanner input, String[][] users, int size, String[][] friendRequest, int currentUser)
    {
        int i = 0;
        boolean done = false;
        int command;
        while (!done)
        {
            System.out.print("Insert the e-mail of the person you would like to add:\t");
            input.skip("\n");
            String friendEmail = input.nextLine();
            int friendIndex = searchUsers(friendEmail, users, size, 0); //0 -> Email

            if(friendIndex != -9999)
            {
                while(friendRequest[friendIndex][i] != null)
                {
                    i++;
                }
                friendRequest[friendIndex][i] = users[currentUser][2]; //Writes the users name on the friend's friend request
                System.out.println("Friend request sent!");
                done = true;
            }
            else
            {
                System.out.println("That isn`t an available user email. Retry?\n1 - YES\n0 - NO");
                command = input.nextInt();

                if(command == 0) done = true;
            }
        }
    }

    public static void checkFriendsRequest(Scanner input, String[][] users, int size, String[][] friendRequest, int currentUser,
                                           String[][] friendList)
    {

            int i = 0, j = 0;
            while(i < SIZE_MAX-1)
            {
                if(friendRequest[currentUser][i] != null)
                {
                    int indexFriend = searchUsers(friendRequest[currentUser][i], users, size, 2); //2 -> name
                    System.out.println(friendRequest[currentUser][i] + "( " + users[indexFriend][0] + " )" +
                            " would like to have you as a friend.\nWould you like to add "
                            + friendRequest[currentUser][i] + " as a friend?\n0 - NO\n1 - YES");
                    int command = input.nextInt();
                    input.skip("\n");

                    if(command == 1)
                    {
                        //Adding friend in user's friend list
                        j = 0;
                        while(friendList[currentUser][j] != null)
                        {
                            j++;
                        }

                        friendList[currentUser][j] = users[indexFriend][0]; //0 -> email
                        users[currentUser][3] = Integer.toString(Integer.parseInt(users[currentUser][3]) + 1); //3 -> friends total

                        //Adding user in friend's friend list
                        j = 0;
                        while(friendList[indexFriend][j] != null)
                        {
                            j++;
                        }
                        friendList[indexFriend][j] = users[currentUser][0];
                        users[indexFriend][3] = Integer.toString(Integer.parseInt(users[indexFriend][3]) + 1);
                        System.out.println("Friend request Accepted!");
                    }
                    else
                    {
                        System.out.println("Friend request declined.");
                    }

                    friendRequest[currentUser][i] = null;
                }
                i++;
            }
            System.out.println("You don't have anymore friend's request.");

    }

    public static void createCommunity(Scanner input, String[][] users, int currentUser, String[][] communityList, String[][] myCommunities)
    {
        input.skip("\n");
        int i = 0;
        while(communityList[i][0] != null)
        {
            i++;
        }

        System.out.print("Insert the community's name:\t");
        communityList[i][0] = input.nextLine(); //NAME
        System.out.print("Add a quick description to your community:\t");
        communityList[i][1] = input.nextLine(); //DESC
        communityList[i][2] = users[currentUser][0]; //ADM
        communityList[i][3] = "1"; //COUNT
        int j = 0;
        while(myCommunities[currentUser][j] != null)
        {
            j++;
        }
        myCommunities[currentUser][j] = communityList[i][0];

        System.out.println("Community created!\n");


    }
    public static void addMemberCommunity(Scanner input, String[][] users, int currentUser, String[][] communityList, String[][] myCommunities)
    {
        int i = 0;
        int command = 1;
        String comName;
        int comIndex;
        boolean noCommunities = true;

        System.out.println("COMMUNITY LIST:");
        while(i < SIZE_MAX-1)
        {
            if(communityList[i][0] != null)
            {
                System.out.println(communityList[i][0]+ "  ->  "+communityList[i][1]);//name -> description
                noCommunities = false;
            }
            i++;
        }
        System.out.println();
        if(!noCommunities)
        {

            do
            {
                System.out.print("Insert the name of the community you would like to enter:\t");
                input.skip("\n");
                comName = input.nextLine();

                comIndex = searchUsers(comName, communityList, SIZE_MAX, 0);
                if(comIndex == -9999)
                {
                    System.out.println("There's no community with that name! Would like to retry?\n1 - YES\n0 - NO");
                    command = input.nextInt();
                }
            }while (comIndex == -9999 && command == 1);

            if(comIndex != -9999)
            {
                int j = 4;
                while(communityList[comIndex][j] != null) //search the position to insert de users name
                {
                    j++;
                }
                communityList[comIndex][j] = users[currentUser][0];
                //increment total members
                int curMemberAmount = Integer.parseInt(communityList[comIndex][3]);
                curMemberAmount++;
                communityList[comIndex][3] = Integer.toString(curMemberAmount);

                j = 0;
                while (myCommunities[currentUser][j] != null)
                {
                    j++;
                }
                myCommunities[currentUser][j] = comName;

                System.out.println("You just entered the community.\n");
            }
        }
        else
        {
            System.out.println("There is no communities registered :(\nMaybe you should create one!");
        }

    }
    public static void editAttribute(Scanner input, String[][] users, int currentUser, String[][][] attributeUsers)
    {
        int i = 0;
        int command;
        int subCommand;
        int attributeIndex;
        String attributeInfo;
        String attributeEdit;
        boolean done = false;
        boolean noAttribute = true;

        while(i < SIZE_MAX)
        {
            if(attributeUsers[currentUser][i][0] != null)
            {
                noAttribute = false;
            }
            i++;
        }

        if(!noAttribute) //Would you like to EDIT?
        {
            System.out.print("Would you like to edit any attribute?\n1 - YES\n0 - NO\n");
            command = input.nextInt();
            input.skip("\n");
            if(command == 1)
            {
                while(!done)
                {
                    System.out.print("What`s the attribute you would like to edit?");
                    attributeEdit = input.nextLine();
                    attributeIndex = searchAttribute(attributeEdit,attributeUsers,currentUser,i,0);
                    if(attributeIndex == -9999)
                    {
                        System.out.println("There's no attribute named like that. Retry?\n1 - YES\n0 - NO");
                        subCommand = input.nextInt();
                        input.skip("\n");
                        if(subCommand == 0) done = true;
                    }
                    else
                    {
                        System.out.print("Insert the new detail about your attribute\t");
                        attributeInfo = input.nextLine();
                        attributeUsers[currentUser][attributeIndex][1] = attributeInfo;

                        System.out.println("Would you like to edit anymore details?\n1 - YES\n0 - NO");
                        subCommand = input.nextInt();
                        input.skip("\n");
                        if(subCommand == 0) done = true;
                    }
                }

            }
        }

        done = false;
        System.out.print("Would you like to add more attributes?\n1 - YES\n0 - NO\n");
        command = input.nextInt();
        input.skip("\n");
        if(command == 1)
        {
            while(!done)
            {
                System.out.print("What attribute would you like to add (Ex: Height, Weight, Civil Status ...)?\t");
                attributeEdit = input.nextLine();
                i = 0;
                while(attributeUsers[currentUser][i][0] != null)
                {
                    i++;
                }
                attributeUsers[currentUser][i][0] = attributeEdit;
                System.out.print("Add the attribute info (Ex: 1,70m, 65kg, Married ..):\t");
                attributeInfo = input.nextLine();
                attributeUsers[currentUser][i][1] = attributeInfo;

                System.out.println("Would you like to add more?\n1 - YES\n0 - NO");
                command = input.nextInt();
                input.skip("\n");

                if(command == 0) done = true;

            }
        }
    }
    public static void sendMessage(Scanner input, String[][] users, int currentUser, String[][][] myMessages, String receiverEmail, int size)
    {
        String messageSending;
        int receiverIndex = searchUsers(receiverEmail, users, size,0); //-> 0 = email
        System.out.print("Insert message:\t");
        messageSending = input.nextLine();

        int i = 0;
        while(myMessages[receiverIndex][i][0] != null)
        {
            i++;
        }
        myMessages[receiverIndex][i][0] = messageSending;
        myMessages[receiverIndex][i][1] = users[currentUser][0];
        users[receiverIndex][4] = Integer.toString(Integer.parseInt(users[receiverIndex][4]) + 1);

        System.out.println("Message Sent!");


    }
    public static void sendMessageToCommunity(Scanner input, String[][] users, int currentUser, String[][][] myMessages, String receiverCommunity, int size,
                                              String[][] communityList, String[][] myCommunities)
    {
        int comIndex = searchUsers(receiverCommunity, communityList, SIZE_MAX, 0); // 0->name of community
        int receiverIndex;
        System.out.println("Insert your message here:");
        String message = input.nextLine();
        int i = 2; // starts sending to the ADM
        int j;
        while(i < SIZE_MAX)
        {
            if(communityList[comIndex][i] != null && i != 3)
            {
                if(!communityList[comIndex][i].equals(users[currentUser][0])) //if it's not myself, ill send the message
                {
                    receiverIndex = searchUsers(communityList[comIndex][i],users, size, 0);//search by the emsil
                    //search on receiver's inbox a space to place the message
                    j = 0;
                    while (myMessages[receiverIndex][j][0] != null)
                    {
                        j++;
                    }
                    myMessages[receiverIndex][j][0] = message;
                    myMessages[receiverIndex][j][1] = users[currentUser][0];
                    myMessages[receiverIndex][j][2] = receiverCommunity;
                    users[receiverIndex][4] = Integer.toString(Integer.parseInt(users[receiverIndex][4]) + 1);

                }
            }

            i++;
        }
        System.out.println("Message sent!");

    }
    public static void messageInbox(Scanner input, String[][] users, int currentUser, String[][][] myMessages, int size, String[][] communityList, String[][] myCommunities)
    {
        boolean isFromCommunity;

        if(users[currentUser][4] == "0" )
        {
            System.out.println("Inbox empty.");
        }
        else
        {
            int i = 0;
            int command;
            while(i < SIZE_MAX)
            {
                if(myMessages[currentUser][i][0] != null)
                {
                    isFromCommunity = false;
                    if(myMessages[currentUser][i][2] != null)
                    {
                        isFromCommunity = true;
                    }
                    System.out.println("--------------------");
                    System.out.print("Message from: " + myMessages[currentUser][i][1]);
                    if (isFromCommunity) System.out.print(" (at " + myMessages[currentUser][i][2] + ")");
                    System.out.println("\n " + myMessages[currentUser][i][0]+"\n--------------------");


                    System.out.println("Would you like to reply?\n1 - YES\n0 - NO");
                    command = input.nextInt();
                    input.skip("\n");

                    users[currentUser][4] = Integer.toString(Integer.parseInt(users[currentUser][4]) - 1);
                    if(command == 1)
                    {
                        if(isFromCommunity)
                        {
                            sendMessageToCommunity(input, users, currentUser, myMessages,  myMessages[currentUser][i][2],size,communityList,myCommunities);
                        }
                        else
                            {
                            sendMessage(input, users, currentUser, myMessages, myMessages[currentUser][i][1], size);
                        }
                    }
                    //deleting message
                    myMessages[currentUser][i][0] = null;
                    myMessages[currentUser][i][1] = null;
                    myMessages[currentUser][i][2] = null;
                }
                i++;
            }

            System.out.println("Inbox empty.");


        }
    }
    public static void sendMessageFromFriendsList(Scanner input, String[][] users, int currentUser,
                                                  String[][][] myMessages, String[][] friendList, int size)
    {
        int i = 0;
        int command;
        boolean thereIsFriends = false;
        boolean friendFound = false;
        String receiverEmail;
        System.out.println("Friends List:");
        while (i < size)
        {
            if(friendList[currentUser][i] != null)
            {
                thereIsFriends = true;
                System.out.println(friendList[currentUser][i]);
            }

            i++;
        }
        if(thereIsFriends)
        {
            while(!friendFound)
            {
                System.out.print("Insert the receiver's e-mail:\t");
                receiverEmail = input.nextLine();
                i = 0;
                while(i < size)
                {
                    if(friendList[currentUser][i] != null)
                    {
                        if(friendList[currentUser][i].equals(receiverEmail))
                        {
                            sendMessage(input, users, currentUser, myMessages, receiverEmail, size);
                            friendFound = true;
                            break;
                        }
                    }
                    i++;
                }

                if(!friendFound)
                {
                    System.out.println("Invalid e-mail. Want to retry?\n1 - YES\n0 - NO");
                    command = input.nextInt();
                    input.skip("\n");
                    if(command == 0) friendFound = true;
                }
            }
        }
        else
        {
            System.out.println("You don't have any friends. Please add someone first.");
        }
    }
    public static void sendMessageFromCommunityList(Scanner input, String[][] users, int currentUser, String[][][] myMessages,
                                            String[][] friendList, int size,String[][] communityList, String[][] myCommunities)
    {
        int i = 0;
        int command;
        boolean thereIsCommunity = false;
        boolean foundCommunity = false;

        String receiverCommunity;
        System.out.println("Community List:");

        while (myCommunities[currentUser][i] != null)
        {
            thereIsCommunity = true;
            System.out.println(myCommunities[currentUser][i]);

            i++;
        }

        if(thereIsCommunity)
        {
            while(!foundCommunity)
            {
                System.out.print("Insert the name of the community you would like to send message to:\t");
                receiverCommunity = input.nextLine();
                i = 0;
                while (myCommunities[currentUser][i] != null)
                {
                    if(myCommunities[currentUser][i].equals(receiverCommunity))
                    {
                        sendMessageToCommunity(input, users, currentUser, myMessages, receiverCommunity,size, communityList, myCommunities);
                        foundCommunity = true;
                        break;
                    }
                    i++;
                }
                if(!foundCommunity)
                {
                    System.out.println("Invalid community. Want to retry?\n1 - YES\n0 - NO");
                    command = input.nextInt();
                    input.skip("\n");
                    if(command == 0) foundCommunity = true;
                }
            }
        }
        else
        {
            System.out.println("You are not in a community. Try enter in one first or create one yourself!");
        }
    }
    public static void messagesManaging(Scanner input, String[][] users, int currentUser, String[][][] myMessages,
                                        String[][] friendList, int size, String[][] communityList, String[][] myCommunities)
    {
        int command;
        System.out.println("Would you like to:\n1 - Check inbox (" + users[currentUser][4] + ")\n2 - Send message to a friend\n" +
                "3 - Send message to community");
        command = input.nextInt();
        input.skip("\n");
        switch (command)
        {
            case 1:
                messageInbox(input, users, currentUser, myMessages, size, communityList, myCommunities);
                break;
            case 2:
                sendMessageFromFriendsList(input, users, currentUser, myMessages, friendList, size);
                break;
            case 3:
                sendMessageFromCommunityList(input,users,currentUser,myMessages,friendList,size,communityList,myCommunities);
                break;
        }

    }
    public static void userProfile(Scanner input, String[][] users, int currentUser,int size,
                                   String[][][] attributeUsers, String[][] myCommunities, String[][] friendList)
    {
        System.out.println("            PROFILE:");
        System.out.println("~ Name:\t" + users[currentUser][2]);
        System.out.println("~ E-mail:\t" + users[currentUser][0]);
        System.out.println("~ Attributes:");
        printAttributesUser(attributeUsers, currentUser);
        System.out.println("~ Communities:");
        int i = 0;
        while(i < SIZE_MAX)
        {
            if(myCommunities[currentUser][i] != null)
            {
                System.out.println(myCommunities[currentUser][i]);
            }
            i++;
        }
        System.out.println("~ Friends List (" + users[currentUser][3] + ") :");
        i = 0;
        while (i < size)
        {
            if(friendList[currentUser][i] != null)
            {
                System.out.println(friendList[currentUser][i]);
            }

            i++;
        }
    }
    public  static void deleteMyMessages(String[][] users, int currentUser,int size, String[][][] myMessages)
    {
        for (int i = 0; i < size; i++)
        {
            for(int j = 0; j < SIZE_MAX; j++)
            {
                if(myMessages[i][j][0] != null)
                {
                    //if im seeing myself, i'll delete my inbox
                    if(i == currentUser)
                    {
                        myMessages[i][j][0] = null;
                        myMessages[i][j][1] = null;
                        myMessages[i][j][2] = null;
                    }
                    //if the sender is the currentUser
                    else if(myMessages[i][j][1].equals(users[currentUser][0]))
                    {
                        myMessages[i][j][0] = null;
                        myMessages[i][j][1] = null;
                        myMessages[i][j][2] = null;
                        //decrement receiver's messages amount
                        users[i][4] = Integer.toString(Integer.parseInt(users[i][4]) - 1);
                    }
                }
            }
        }
    }
    public static void deleteUserCommunity(String[][] users, int currentUser,int size, String[][] myCommunities, String[][] communityList)
    {
        int i = 0, j = 0;
        int comIndex;

        while(myCommunities[currentUser][j] != null)
        {
            comIndex = searchUsers(myCommunities[currentUser][j],communityList,SIZE_MAX,0);
            //remove from members amount
            communityList[comIndex][3] = Integer.toString(Integer.parseInt(communityList[comIndex][3]) - 1);
            //if the user is the ADM of the community
            if(communityList[comIndex][2].equals(users[currentUser][0]))
            {
                //search nearest member to be the the new ADM
                i = 4;
                while(communityList[comIndex][i] == null && i < SIZE_MAX-1)
                {
                    i++;
                }
                //no members left
                if(i == SIZE_MAX-1)
                {
                    //delete the community
                    communityList[comIndex][0] = null;
                    communityList[comIndex][1] = null;
                    communityList[comIndex][2] = null;
                    communityList[comIndex][3] = null;
                }
                else
                {
                    communityList[comIndex][2] = communityList[comIndex][i];
                    communityList[comIndex][i] = null;
                }

            }
            //user is not the ADM
            else
            {
                //search the position of current User
                i = 4;
                while (i < SIZE_MAX-1)
                {
                    if(communityList[comIndex][i].equals(users[currentUser][0])) break;
                    i++;
                }
                communityList[comIndex][i] = null;
            }

            myCommunities[currentUser][j] = null;
            j++;
        }
        /*
        //deleting myCommunitylist
        j = 0;
        while(myCommunities[currentUser][j] != null)
        {
            myCommunities[currentUser][j] = null;

            j++;
        }*/
    }
    public static void deleteUserFriend(String[][] users, int currentUser,int size, String[][] friendList, String[][] friendRequest)
    {
        //deleting friends request and user's presence on other friends list

        for(int i = 0;i < size;i++)
        {
            for (int j = 0; j < SIZE_MAX; j++)
            {
                if(friendRequest[i][j] != null)
                {
                    //if friend request was sent by the current user, delete it
                    if(friendRequest[i][j].equals(users[currentUser][2]))
                    {
                        friendRequest[i][j] = null;
                    }
                }
                if(friendList[i][j] != null)
                {
                    //if current user is on the person's list, delete it
                    if(friendList[i][j].equals(users[currentUser][0]))
                    {
                        friendList[i][j] = null;
                        users[i][3] = Integer.toString(Integer.parseInt(users[i][3]) - 1); //decrement friend amount
                    }
                }
            }
        }
        //deleting friend list and friend request of current user
        int j = 0;
        while(j < SIZE_MAX)
        {
            friendList[currentUser][j] = null;
            friendRequest[currentUser][j] = null;
            j++;
        }

    }
    public  static void deleteProfile(String[][] users, int currentUser,int size, String[][][] attributeUsers)
    {
        int j = 0;
        while(j < SIZE_MAX)
        {
            if(j <= 4)
            {
                users[currentUser][j] = null;
            }
            attributeUsers[currentUser][j][0] = null;
            attributeUsers[currentUser][j][1] = null;
            j++;
        }
    }
    public static void removeUser(Scanner input, String[][] users, int currentUser,int size, String[][][] attributeUsers,
                                  String[][] myCommunities, String[][] communityList, String[][] friendList, String[][] friendRequest,
                                  String[][][] myMessages)
    {
        int command;
        System.out.println("Are you sure you would like to remove your account?\n1 - YES\n0 - NO");
        command = input.nextInt();
        if(command == 1)
        {
            System.out.println("Deleting you account ...\n1 - Continue\n0 - Cancel");
            command = input.nextInt();
            input.skip("\n");
            if(command == 1)
            {
                //delete messages
                deleteMyMessages(users, currentUser, size, myMessages);
                //delete user's communities
                deleteUserCommunity(users, currentUser, size, myCommunities, communityList);
                //delete user's relationship
                deleteUserFriend(users, currentUser, size, friendList, friendRequest);
                //delete profile
                deleteProfile(users, currentUser, size, attributeUsers);
                System.out.println("Account deleted successfully!");

                System.out.println("See ya!");
            }
        }
    }
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int command = -9999;
        int loggedInUser = -9999;
        boolean doneLogInMenu = false;
        boolean doneUserMenu = true;
        String[][] users = new String[30][30]; // [user][info]
        String[][][] attributeUsers = new String[30][30][2]; //[][][0] - Attribute Name; [][][1] - attribute;
        String[][] friendRequest = new String[30][30];
        String[][] friendList = new String[30][30];
        String[][] communityList = new String[30][30]; //0 - > name; 1 -> description; 2-> ADM; 3->count; 4+->members
        String[][] myCommunities = new String[30][30];
        String[][][] myMessages = new String[30][100][3]; //[][][x] -> From.

        int arraySize = 0;

        while(!doneLogInMenu)
        {
            try
            {
                command = menuIFace(input);

                switch(command)
                {
                    case 0:
                        doneLogInMenu = true;
                        break;
                    case 1:
                        loggedInUser = logInUser(input, users, arraySize);
                        if(loggedInUser != -9999) doneUserMenu = false;
                        break;
                    case 2:
                        signUpUser(input, users, arraySize);
                        arraySize++;
                        break;
                }

                ///USER MENU
                while(!doneUserMenu)
                {
                    try
                    {
                        command = menuUser(input, users, loggedInUser);
                        switch(command)
                        {
                            case 0:
                                System.out.println("See ya!");
                                doneUserMenu = true;
                                break;
                            case 1: //add friend
                                addFriend(input, users, arraySize, friendRequest, loggedInUser);
                                break;
                            case 2: //friends request
                                checkFriendsRequest(input, users, arraySize, friendRequest, loggedInUser, friendList);
                                break;
                            case 3: //Message
                                messagesManaging(input, users, loggedInUser, myMessages, friendList, arraySize,communityList,myCommunities);
                                break;
                            case 4: //Community creation
                                createCommunity(input,users,loggedInUser,communityList,myCommunities);
                                break;
                            case 5: //Enter community
                                addMemberCommunity(input, users, loggedInUser, communityList, myCommunities);
                                break;
                            case 6: //See profile
                                userProfile(input, users, loggedInUser, arraySize, attributeUsers, myCommunities, friendList);
                                break;
                            case 7: //Edit Attribute
                                editAttribute(input, users, loggedInUser, attributeUsers);
                                break;
                            case 8: //Delete account
                                removeUser(input, users, loggedInUser, arraySize, attributeUsers, myCommunities,
                                        communityList, friendList, friendRequest, myMessages);
                                if(users[loggedInUser][0] == null) doneUserMenu = true; //means it's deleted
                                break;
                        }
                    }
                    catch(Exception e)
                    {
                        System.out.println("           >>>Invalid input!<<<");
                        input.next();
                    }
                }
            }
            catch (Exception e)
            {
                System.out.println("           >>>Invalid input!<<<");
                input.next();
            }
        }




    }


}

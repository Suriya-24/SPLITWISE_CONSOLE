import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;
import java.util.*;
class person
{
    String name;
    String password;
    String email;
    ArrayList<group>arr=new ArrayList<>();
    person(String name,String password,String email)
    {
        this.name=name;
        this.password=password;
        this.email=email;
    }
    void add_group(group g)
    {
        this.arr.add(g);
    }
    void display()
    {
        for(group g:arr)
        {
            g.display();
        }
    }
    void grp_delete(group g)
    {
        for(group grp:arr)
        {
            if(grp.group_name.equals(g.group_name))
            {
                arr.remove(grp);
                break;
            }
        }
    }
}
class group
{
    String group_name;
    ArrayList<person> arr=new ArrayList<>();
    int expenses;
    HashMap<person,Integer>exp_map=new HashMap<>();

    group(String name,ArrayList<person>p)
    {
        this.group_name=name;
        this.arr=p;
    }
    void add_expense(String name,int value)
    {
        for(person p:arr)
        {
            if(p.name.equals(name))
            {
                exp_map.put(p,0);
            }
            else
            {
                exp_map.put(p,value);
            }
        }
    }
    void add_gmember(person p)
    {
        arr.add(p);
    }


    void display()
    {
        System.out.println("GROUP NAME:"+group_name);
        int num=1;
        for(person p:arr)
        {
            String expense="";
            if(exp_map.get(p)==null)expense="0";
            else expense=String.valueOf(exp_map.get(p));
            System.out.println(num+"."+p.name+" "+"EXPENSE: "+expense);
            num++;
        }
    }
    void settle(String name)
    {
        for(person p:arr)
        {
            exp_map.put(p,0);
        }
    }
    void delete(person p)
    {
        arr.remove(p);
    }

}
public class splitwise_console
{
    static ArrayList<group>Group=new ArrayList<>();
    static ArrayList<person>person=new ArrayList<>();
    static HashMap<String,String>user_map=new HashMap<>();
    static HashMap<String,person>person_map=new HashMap<>();
    static boolean enter(String name,String pass)
    {
        for(person p:person)
        {
            if(p.name.equals(name))
            {
                if(p.password.equals(pass))return true;
            }
        }
        return false;

    }
    static String signin()
    {
        Scanner sign=new Scanner(System.in);
        System.out.println("ENTER YOUR USER_NAME:");
        String name=sign.next();
        System.out.println("ENTER YOUR PASSWORD:");
        String pass=sign.next();
        boolean login_pass=enter(name,pass);
        if(!login_pass)
        {
            System.out.println("INCORRECT NAME OR PASSWORD");
            return "NULL";
        }
        else
        {
            System.out.println("LOGGED IN SUCESSFULLY");
            return name;
        }
    }
    static void create_group(String name)
    {
        ArrayList<String>members=new ArrayList<>();
        members.add(name);
        Set<String>set=new HashSet<>();
        set.add(name);
        Scanner c_group=new Scanner(System.in);
        System.out.println("ENTER GROUP NAME:");
        String grp_name=c_group.next();
        boolean grp_create=true;
        while(grp_create)
        {
            System.out.println("1.ADD MEMBER \n2.EXIT");
            String choice = c_group.next();
            if (choice.equals("1"))
            {
                System.out.println("ENTER MEMBER NAME:");
                String member_name = c_group.next();
                if (user_map.containsKey(member_name))
                {
                    if (!set.contains(member_name))
                    {
                        members.add(member_name);
                        set.add(member_name);
                    } else
                    {
                        System.out.println("THAT MEMBER IS ALREADY IN THAT GROUP");
                    }
                }
                else
                {
                    System.out.println("NO USER FOUND");
                }
            }
            if(choice.equals("2"))
            {
                grp_create=false;
                System.out.println("GROUP CREATED SUCCESSFULLY");
            }
            if(!choice.equals("1") && !choice.equals("2"))System.out.println("Invalid command");
        }
        ArrayList<person>temp=new ArrayList<>();
        for(String str:members)
        {
            for(person p:person)
            {
                if(p.name.equals(str))
                {
                    temp.add(p);
                    break;
                }
            }
        }
        group g=new group(grp_name,temp);
        Group.add(g);
        for(String str:members)
        {
            for(person p:person)
            {
                if(str.equals(p.name))
                {
                    p.add_group(g);
                }
            }
        }
    }
    static void mygrp_display(String name)
    {
        int count=0;
        for (person p : person)
        {
            if (p.name.equals(name))
            {
                p.display();
                count++;
            }
        }
        System.out.println("---------------------");
        if(count==0)
        {
            System.out.println("NO GROUPS!!!");
            System.out.println("-------------------");
        }
    }
    static void acc_create()
    {
        Scanner acc_create=new Scanner(System.in);
        System.out.println("ENTER YOUR USER_NAME:");
        String name="";
        boolean name_pass=true;
        while(name_pass)
        {
            name = acc_create.next();
            if (user_map.containsKey(name))
            {
                System.out.println("SORRY THAT USER_NAME IS NOT AVAILABLE");
            }
            else
            {
                name_pass=false;
            }
        }
        System.out.println("ENTER YOUR PASSWORD:");
        String pass=acc_create.next();
        System.out.println("ENTER YOUR EMAIL:");
        String email=acc_create.next();
        person.add(new person(name,pass,email));
        user_map.put(name,pass);
        for(person p:person)
        {
            if(p.name.equals(name))
            {
                person_map.put(name,p);
                break;
            }
        }
        System.out.println("ACCOUNT CREATED SUCESSFULLY");
        System.out.println("---------------------------");
    }
    static void leave_group(String name)
    {
        Scanner l_g=new Scanner(System.in);
        mygrp_display(name);
        person temp=null;
        for(person p:person)if(p.name.equals(name))temp=p;
        System.out.println("ENTER THE GROUP NAME TO LEAVE:");
        String choice=l_g.next();
        for(group g:Group)
        {
            if(g.group_name.equals(choice))
            {
                g.delete(temp);
                temp.grp_delete(g);
            }
        }
        System.out.println("SUCESSFULLY EXITED GROUP");
    }
    static void add_expenses(String name)
    {
        Scanner expense=new Scanner(System.in);
        System.out.println("GROUP NAMES:");
        mygrp_display(name);
        System.out.println("ENTER A GROUP NAME TO SHARE EXPENSES:");
        String grp_name=expense.next();
        System.out.println("ENTER A AMOUNT TO SPLIT:");
        int exp=Integer.parseInt(expense.next());
        String group_name="";
        int num=0;
        boolean grp_find=true;
        for(group g:Group)
        {
            if(grp_name.equals(g.group_name))
            {
                num=g.arr.size();
                int ex=exp/num;
                g.add_expense(name,ex);
                grp_find=false;
            }
        }
        if(grp_find)
        {
            System.out.println("NO GROUP FOUND");
        }

    }
    static void settle_up(String name)
    {
        mygrp_display(name);
        Scanner set=new Scanner(System.in);
        System.out.println("ENTER THE GROUP NAME TO SETTLE:");
        String grp_name=set.next();
        for(group g:Group)
        {
            if(grp_name.equals(g.group_name))
            {
                g.settle(name);
            }
        }
    }
    static void add_member(String name)
    {
        Scanner add_member_input=new Scanner(System.in);
        for(person p:person)
        {
            if(p.name.equals(name))
            {
                p.display();
                break;
            }
        }
        System.out.println("ENTER THE GROUP NAME TO ADD MEMBER:");
        String str_gname=add_member_input.next();
        System.out.println("ENTER THE PERSON NAME:");
        String str_pname=add_member_input.next();
        for(group g:Group)
        {
            if(g.group_name.equals(str_gname))
            {
                for(person p:person)
                {
                    if(p.name.equals(str_pname))
                    {
                        g.add_gmember(p);
                    }
                }
            }
        }
    }
    public static void main(String[] args)
    {
        Scanner in=new Scanner(System.in);
        boolean main_flag=true;
        String choice="";
        while(main_flag)
        {
            System.out.println("1.SIGNIN \n2.SIGNUP \n3.EXIT");
            choice=in.next();
            if(choice.equals("1"))
            {
                String name=signin();
                while(name.equals("NULL"))name=signin();
                boolean user_flag=true;
                while(user_flag)
                {
                    System.out.println("1.CREATE GROUP \n2.MY GROUPS \n3.LEAVE GROUP \n4.ADD EXPENSES \n5.SETTLE \n6.ADD MEMBER \n7.LOGOUT");
                    choice = in.next();
                    if (choice.equals("1")) create_group(name);
                    if (choice.equals("2")) mygrp_display(name);
                    if (choice.equals("3")) leave_group(name);
                    if (choice.equals("4")) add_expenses(name);
                    if(choice.equals("5"))settle_up(name);
                    if(choice.equals("7"))user_flag=false;
                    if(choice.equals("6"))add_member(name);
                    if (!choice.equals("1") && !choice.equals("2") && !choice.equals("3") && !choice.equals("4") && !choice.equals("5") && !choice.equals("6")  &&!choice.equals("7"))System.out.println("INVALID COMMAND");
                }
            }
            if(choice.equals("2"))
            {
                acc_create();
            }
            if(choice.equals("3"))
            {
                main_flag=false;
            }
        }
    }
}

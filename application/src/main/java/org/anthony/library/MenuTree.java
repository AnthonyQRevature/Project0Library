package org.anthony.library;

import java.io.Serializable;

@SuppressWarnings("unused")
public class MenuTree implements Serializable 
{

    public MenuTree(Entry menu) {
        this.menu = menu;
    }
    public MenuTree() {
    }

    public Entry getMenu() {
        return menu;
    }
    public void setMenu(Entry menu) {
        this.menu = menu;
    }

    public Entry getMenu_login() {
        return menu_login;
    }
    public void setMenu_login(Entry menu_login) {
        this.menu_login = menu_login;
    }

    public static class Entry implements Serializable
    {
        public Entry() {
        }

        String name;
        String header;
        String service_request = null;
        String content_request = null;
        int level;
        Entry[] options;
        transient Entry parent;

        public Entry(String name, String header)
        {
            this.name = name;
            this.header = header;

            parent = null;
        }

        public String getName() {
            return name;
        }
        public String getHeader() {
            return header;
        }
        public Entry[] getOptions() {
            return options;
        }
        public void setName(String name) {
            this.name = name;
        }
        public void setHeader(String header) {
            this.header = header;
        }
        public void setOptions(Entry[] options) {
            for (Entry entry : options) {
                entry.parent = this;
            }
            this.options = options;
        }
        public int getLevel() {
            return level;
        }
        public void setLevel(int level) {
            this.level = level;
        }
        public Entry getParent() {
            return parent;
        }

        public String getService_request() {
            return service_request;
        }

        public void setService_request(String service_request) {
            this.service_request = service_request;
        }

        public String getContent_request() {
            return content_request;
        }

        public void setContent_request(String content_request) {
            this.content_request = content_request;
        }
    }
    
    public Entry menu;
    public Entry menu_login;
    public Entry menu_book;
}

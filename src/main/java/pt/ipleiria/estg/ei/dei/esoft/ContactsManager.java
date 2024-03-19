package pt.ipleiria.estg.ei.dei.esoft;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

public class ContactsManager {
    private List<Contact> contacts;
    private HashMap<String, List<Contact>> labels;
    public ContactsManager() {
        contacts = new LinkedList<>();
        labels = new HashMap<>(200);
    }
    public List<String> getLabels() {
        return (List<String>) labels.keySet();

    }
    public List<Contact> getContacts(String... labels) {
        if(labels.length == 0) {
            return contacts;
        }
        List<Contact> result = new LinkedList<>();
        for (String label : labels) {
            result.addAll(this.labels.get(label));
        }
        return result;
    }
    public List<Contact> search(String term, String... labels) {
        List<Contact> contacts = getContacts(labels);
        List<Contact> result = new LinkedList<>();
        for (Contact c : contacts){
            if(c.getFirstName().contains(term) || c.getLastName().contains(term) || c.getPhone().contains(term) || c.getEmail().contains(term)){
                result.add(c);
            }
        }
        return result;
    }
    public void addContact(Contact contact, String... labels) {
        Predicate<Contact> duplicate = c -> Objects.equals(c.getPhone(),
                contact.getPhone()) && Objects.equals(c.getEmail(), contact.getEmail());

        if (contacts.stream().noneMatch(duplicate)) contacts.add(contact);
        if (labels.length == 0) return;
        for (var label : labels) {
            if (!this.labels.containsKey(label)) {
                this.labels.put(label, new LinkedList<>());
            }
            var contactsLabel = this.labels.get(label);
            if (!contacts.stream().noneMatch(duplicate)) {
                contactsLabel.add(contact);
            }
        }
    }

    public void removeContact(Contact contact) {
        contacts.remove(contact);
        for (List<Contact> list : labels.values()) {
            list.remove(contact);
        }
    }

    public int size() {
        return contacts.size();
    }

    public boolean isEmpty() {
        return contacts.isEmpty();

    }
}
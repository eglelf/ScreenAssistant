package screenassistant.Collections;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import screenassistant.model.StringTable;
import screenassistant.model.StringTableComparator;

public class StringTables {

    private ArrayList<StringTable> stringTables;

    public StringTables() {
        this.stringTables = new ArrayList<StringTable>();
    }

    public StringTables(List<StringTable> stringTables) {
        this.stringTables = new ArrayList<>();
        this.stringTables.addAll(stringTables);
    }

    public ArrayList<StringTable> getStringTables() {
        return stringTables;
    }

    public void sort() {
        Collections.sort(stringTables, new StringTableComparator());
    }

    public List<StringTable> buscar(String txt, boolean key, boolean chave, boolean caseSensitive) {
        if (txt.isEmpty()) {
            return this.stringTables;
        }

        if (caseSensitive) {
            if (key && chave) {
                return buscarPeloChaveEhValorCaseSensitive(txt);
            } else if (key) {
                return buscarPeloChaveEhValorCaseSensitive(txt);
            } else if (chave) {
                return buscarPeloValorCaseSensitive(txt);
            }else{
                return this.stringTables;
            }
        } else {
            if (key && chave) {
                return buscarPeloChaveEhValor(txt);
            } else if (key) {
                return buscarPeloChaveEhValor(txt);
            } else if (chave) {
                return buscarPeloValor(txt);
            }else{
                return this.stringTables;
            }
        }
    }

    private List<StringTable> buscarPelaChave(String txt) {
        String txt2 = txt.toUpperCase();
        ArrayList<StringTable> busca = new ArrayList<>();
        for (StringTable st : stringTables) {
            if (st.getKey().toUpperCase().contains(txt2)) {
                busca.add(st);
            }
        }

        return busca;
    }

    private List<StringTable> buscarPeloValor(String txt) {
        String txt2 = txt.toUpperCase();
        ArrayList<StringTable> busca = new ArrayList<>();
        for (StringTable st : stringTables) {
            if (st.getValue().toUpperCase().contains(txt2)) {
                busca.add(st);
            }
        }

        return busca;
    }

    private List<StringTable> buscarPeloChaveEhValor(String txt) {
        String txt2 = txt.toUpperCase();
        ArrayList<StringTable> busca = new ArrayList<>();
        for (StringTable st : stringTables) {
            if (st.getKey().toUpperCase().contains(txt2) || st.getValue().toUpperCase().contains(txt2)) {
                busca.add(st);
            }
        }

        return busca;
    }

    private List<StringTable> buscarPelaChaveCaseSensitive(String txt) {
        ArrayList<StringTable> busca = new ArrayList<>();
        for (StringTable st : stringTables) {
            if (st.getKey().contains(txt)) {
                busca.add(st);
            }
        }

        return busca;
    }

    private List<StringTable> buscarPeloValorCaseSensitive(String txt) {
        ArrayList<StringTable> busca = new ArrayList<>();
        for (StringTable st : stringTables) {
            if (st.getValue().contains(txt)) {
                busca.add(st);
            }
        }

        return busca;
    }

    private List<StringTable> buscarPeloChaveEhValorCaseSensitive(String txt) {
        ArrayList<StringTable> busca = new ArrayList<>();
        for (StringTable st : stringTables) {
            if (st.getKey().contains(txt) || st.getValue().contains(txt)) {
                busca.add(st);
            }
        }

        return busca;
    }

}

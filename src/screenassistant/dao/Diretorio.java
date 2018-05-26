/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package screenassistant.dao;

import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author Egle
 */
public class Diretorio {

    public Diretorio() {

    }

    public String getDirectoryFileToOpen(String descricao, File diretorioInicial, String... extensoes) throws Exception {

        if (extensoes.length == 0) {
            throw new Exception("Extensões não informadas");
        }

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

        FileNameExtensionFilter filtro;
        filtro = new FileNameExtensionFilter(descricao, extensoes);
        fileChooser.setFileFilter(filtro);
        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.setCurrentDirectory(diretorioInicial);

        int resposta = fileChooser.showOpenDialog(null);

        if (resposta == JFileChooser.ERROR_OPTION) {
            throw new Exception("Erro no diretório selecionado");
        } else if (resposta == JFileChooser.CANCEL_OPTION) {
            throw new Exception("Seleção de diretório cancelada");
        } else if (resposta == JFileChooser.APPROVE_OPTION) {
            File arquivoSelecionado = fileChooser.getSelectedFile();
            String dir = arquivoSelecionado.getCanonicalPath();
            return dir;
        }

        return null;
    }

    public String getDirectoryFileToOpen(String descricao, String... extensoes) throws Exception {

        if (extensoes.length == 0) {
            throw new Exception("Extensões não informadas");
        }

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

        FileNameExtensionFilter filtro;
        filtro = new FileNameExtensionFilter(descricao, extensoes);
        fileChooser.setFileFilter(filtro);
        fileChooser.setAcceptAllFileFilterUsed(false);

        int resposta = fileChooser.showOpenDialog(null);

        if (resposta == JFileChooser.ERROR_OPTION) {
            throw new Exception("Erro no diretório selecionado");
        } else if (resposta == JFileChooser.CANCEL_OPTION) {
            throw new Exception("Seleção de diretório cancelada");
        } else if (resposta == JFileChooser.APPROVE_OPTION) {
            File arquivoSelecionado = fileChooser.getSelectedFile();
            String dir = arquivoSelecionado.getCanonicalPath();
            return dir;
        }

        return null;
    }

    public boolean existeArquivo(String diretorio) {
        File file = new File(diretorio);
        return file.exists();
    }

    public boolean existeDiretorio(String diretorio) {
        File file = new File(diretorio);
        return file.isDirectory();
    }

    public String getDirectoryFileToSave(File diretorioInicial) throws Exception {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        fileChooser.setCurrentDirectory(diretorioInicial);
        
        int resposta = fileChooser.showSaveDialog(null);
        if (resposta == JFileChooser.ERROR_OPTION) {
            throw new Exception("Erro no diretório selecionado");
        } else if (resposta == JFileChooser.CANCEL_OPTION) {
            throw new Exception("Seleção de diretório cancelada");
        } else if (resposta == JFileChooser.APPROVE_OPTION) {
            return fileChooser.getSelectedFile().getAbsolutePath();
        }

        return null;
    }

    public String getDirectoryFileToSave() throws Exception {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int resposta = fileChooser.showSaveDialog(null);

        if (resposta == JFileChooser.ERROR_OPTION) {
            throw new Exception("Erro no diretório selecionado");
        } else if (resposta == JFileChooser.CANCEL_OPTION) {
            throw new Exception("Seleção de diretório cancelada");
        } else if (resposta == JFileChooser.APPROVE_OPTION) {
            return fileChooser.getSelectedFile().getAbsolutePath();
        }

        return null;
    }
}

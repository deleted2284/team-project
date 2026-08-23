public class FileDataSource implements DataSource{
    private String path;

    public FileDataSource(String path) {
        this.path = path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public MyList<Student> fill(int size) {
        return null;
    }
}

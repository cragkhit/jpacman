package nl.tudelft.jpacman.level;

import nl.tudelft.jpacman.PacmanConfigurationException;
import nl.tudelft.jpacman.board.BoardFactory;
import nl.tudelft.jpacman.npc.ghost.Blinky;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MapParserTest {
    @Mock
    private BoardFactory boardFactory;
    @Mock
    private LevelFactory levelFactory;
    @Mock
    private Blinky blinky;

    @Test
    public void testParseMapGood() {
        MockitoAnnotations.initMocks(this);
        assertNotNull(boardFactory);
        assertNotNull(levelFactory);
        when(levelFactory.createGhost()).thenReturn(blinky);
        MapParser mapParser = new MapParser(levelFactory, boardFactory);
        ArrayList<String> map = new ArrayList<>();
        map.add("############");
        map.add("#P        G#");
        map.add("############");
        mapParser.parseMap(map);
        verify(levelFactory, times(1)).createGhost();
        verify(boardFactory, times(1)).createBoard(any());
        verify(levelFactory, times(1)).createLevel(any(), any(), any());
    }

    @Test
    public void testParseMapWrong1() {
        PacmanConfigurationException thrown = Assertions.assertThrows(PacmanConfigurationException.class, () -> {
            MockitoAnnotations.initMocks(this);
            assertNotNull(boardFactory);
            assertNotNull(levelFactory);
            MapParser mapParser = new MapParser(levelFactory, boardFactory);
            ArrayList<String> map = new ArrayList<>();
            map.add("############");
            map.add("#P        X#");
            map.add("############");
            mapParser.parseMap(map);
        });
        Assertions.assertEquals("Invalid character at 10,1: X", thrown.getMessage());
    }
}
